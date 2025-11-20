package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.AnaliseIa;
import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.repository.AnaliseIaRepository;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import br.com.fiap.moodtrack.application.usecase.IaRiskAnalyzer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class RunCheckinAnalysis {

    @Inject CheckinRepository checkinRepo;
    @Inject AnaliseIaRepository analiseRepo;
    @Inject IaRiskAnalyzer iaRiskAnalyzer;

    @Transactional
    public AnaliseIa handle(Long checkinId) {
        Checkin checkin = checkinRepo.findById(checkinId)
                .orElseThrow(() -> new IllegalArgumentException("checkin not found: " + checkinId));

        var result = iaRiskAnalyzer.analyze(checkin);

        AnaliseIa analise = new AnaliseIa();
        analise.setCheckin(checkin);
        analise.setModeloUtilizado("MyAwesomeModel-v1");
        analise.setScoreRisco(result.score());
        analise.setDataAnalise(LocalDateTime.now());
        // analise.setResumo(result.resumo());

        return analiseRepo.save(analise);
    }
}
