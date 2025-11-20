package br.com.fiap.moodtrack.infrastructure.web.dto;

public record AnaliseIaOutputDto(
        Long id,
        Long checkinId,
        String modeloUtilizado,
        Double scoreRisco,
        java.time.LocalDateTime dataAnalise,
        String resumo
) {}