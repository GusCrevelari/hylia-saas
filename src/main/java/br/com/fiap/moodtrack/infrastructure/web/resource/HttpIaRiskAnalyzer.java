package br.com.fiap.moodtrack.infrastructure.web.resource;
import br.com.fiap.moodtrack.application.usecase.IaRiskAnalyzer;
import br.com.fiap.moodtrack.domain.model.Checkin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@ApplicationScoped
public class HttpIaRiskAnalyzer implements IaRiskAnalyzer {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "ia.openai.url")
    String apiUrl;

    @ConfigProperty(name = "ia.openai.model")
    String model;

    @ConfigProperty(name = "ia.openai.api_key")
    String apiKey;

    @Override
    public Result analyze(Checkin c) {
        try {
            // 1) Monta o "prompt" com dados do checkin
            String observacao = c.getObservacao() == null ? "" : c.getObservacao();

            String userPrompt = """
                Você é um especialista em saúde mental e burnout.
                Analise o risco de burnout deste usuário com base nestes dados de check-in:

                - Humor (1-5, quanto maior melhor): %d
                - Energia (1-5, quanto maior melhor): %d
                - Carga de trabalho (1-5, quanto maior, mais pesada): %d
                - Observação livre: "%s"

                Responda **apenas** em JSON válido, no seguinte formato:
                {
                  "score": número entre 0.0 e 1.0,
                  "resumo": "explicação breve em português para o usuário final"
                }
                Não inclua nenhum texto fora do JSON.
                """.formatted(
                    c.getHumor(),
                    c.getEnergia(),
                    c.getCargaTrabalho(),
                    observacao
            );

            // 2) Monta o body da chamada para Chat Completions com JSON mode
            // {
            //   "model": "...",
            //   "response_format": { "type": "json_object" },
            //   "messages": [ ... ]
            // }
            var root = objectMapper.createObjectNode();
            root.put("model", model);

            var responseFormat = objectMapper.createObjectNode();
            responseFormat.put("type", "json_object");
            root.set("response_format", responseFormat);

            var messages = objectMapper.createArrayNode();

            var sys = objectMapper.createObjectNode();
            sys.put("role", "system");
            sys.put("content", "Você é um assistente que avalia risco de burnout com base em dados numéricos e texto.");

            var user = objectMapper.createObjectNode();
            user.put("role", "user");
            user.put("content", userPrompt);

            messages.add(sys);
            messages.add(user);

            root.set("messages", messages);

            String requestBodyJson = objectMapper.writeValueAsString(root);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .timeout(Duration.ofSeconds(30))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // 3) Chama a API da OpenAI
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new RuntimeException("OpenAI API error: HTTP " + response.statusCode()
                        + " - " + response.body());
            }

            // 4) Parse da resposta Chat Completions
            //
            // Estrutura básica:
            // {
            //   "id": "...",
            //   "choices": [
            //     {
            //       "message": {
            //         "role": "assistant",
            //         "content": "{ \"score\": 0.82, \"resumo\": \"...\" }"
            //       }
            //     }
            //   ],
            //   ...
            // }
            //
            JsonNode rootResp = objectMapper.readTree(response.body());
            JsonNode firstChoice = rootResp.path("choices").path(0);
            String content = firstChoice.path("message").path("content").asText();

            if (content == null || content.isBlank()) {
                throw new RuntimeException("Empty content from OpenAI: " + response.body());
            }

            // 5) O content em JSON mode deve ser um JSON puro ⇒ parse de novo
            JsonNode jsonResult = objectMapper.readTree(content);
            double score = jsonResult.path("score").asDouble();
            String resumo = jsonResult.path("resumo").asText();

            // segurança: força 0..1
            if (score < 0.0) score = 0.0;
            if (score > 1.0) score = 1.0;

            if (resumo == null || resumo.isBlank()) {
                resumo = "Não foi possível gerar um resumo detalhado, mas seu score de risco estimado é "
                        + String.format("%.2f", score) + ".";
            }

            return new Result(score, resumo);

        } catch (Exception e) {
            // Em produção, logue com logger; aqui simplificamos
            throw new RuntimeException("Falha ao chamar serviço de IA da OpenAI", e);
        }
    }
}
