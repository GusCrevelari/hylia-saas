package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.application.exception.DuplicateCheckinException;
import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.RiskLevel;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@ApplicationScoped
public class CreateCheckin {

    @Inject CheckinRepository checkinRepository;

    @Transactional
    public Checkin handle(Checkin in) {
        var day = in.getDataCheckin() != null ? in.getDataCheckin().toLocalDate() : LocalDate.now();
        var existing = checkinRepository.findByUsuarioAndDate(in.getUsuario().getId(), day);
        if (existing.isPresent()) throw new DuplicateCheckinException("checkin already exists for day");
        in.setNivelRisco(computeRisk(in.getHumor(), in.getEnergia(), in.getCargaTrabalho()));
        return checkinRepository.save(in);
    }

    private RiskLevel computeRisk(Integer humor, Integer energia, Integer carga) {
        if (humor == null || energia == null || carga == null) return null;
        if (carga >= 4 && energia <= 2) return RiskLevel.VERMELHO;
        if (carga >= 3 || energia <= 3 || humor <= 2) return RiskLevel.AMARELO;
        return RiskLevel.VERDE;
    }
}
