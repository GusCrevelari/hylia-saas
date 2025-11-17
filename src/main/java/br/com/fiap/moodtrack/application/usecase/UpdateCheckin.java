package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.model.RiskLevel;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import br.com.fiap.moodtrack.domain.repository.DicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ApplicationScoped
public class UpdateCheckin {

    @Inject CheckinRepository checkinRepository;
    @Inject DicaRepository dicaRepository;

    @Transactional
    public Checkin handle(Long id,
                          Integer humor,
                          Integer energia,
                          Integer cargaTrabalho,
                          String observacao,
                          Long dicaId,
                          LocalDateTime dataCheckin) {

        Checkin c = checkinRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("checkin not found: " + id));

        boolean changedScores = false;

        if (humor != null) {
            validate15(humor);
            c.setHumor(humor);
            changedScores = true;
        }
        if (energia != null) {
            validate15(energia);
            c.setEnergia(energia);
            changedScores = true;
        }
        if (cargaTrabalho != null) {
            validate15(cargaTrabalho);
            c.setCargaTrabalho(cargaTrabalho);
            changedScores = true;
        }
        if (observacao != null) c.setObservacao(observacao);
        if (dataCheckin != null) c.setDataCheckin(dataCheckin);
        if (dicaId != null) {
            Dica d = dicaRepository.findById(dicaId)
                    .orElseThrow(() -> new NoSuchElementException("tip not found: " + dicaId));
            c.setDica(d);
        }
        if (changedScores) {
            c.setNivelRisco(computeRisk(c.getHumor(), c.getEnergia(), c.getCargaTrabalho()));
        }
        return checkinRepository.save(c);
    }

    private void validate15(Integer v) {
        if (v == null || v < 1 || v > 5) throw new IllegalArgumentException("value must be between 1 and 5");
    }

    private RiskLevel computeRisk(Integer humor, Integer energia, Integer carga) {
        if (humor == null || energia == null || carga == null) return null;
        if (carga >= 4 && energia <= 2) return RiskLevel.VERMELHO;
        if (carga >= 3 || energia <= 3 || humor <= 2) return RiskLevel.AMARELO;
        return RiskLevel.VERDE;
    }
}
