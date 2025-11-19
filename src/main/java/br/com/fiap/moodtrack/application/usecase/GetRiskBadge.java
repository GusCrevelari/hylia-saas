package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.RiskLevel;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import br.com.fiap.moodtrack.infrastructure.web.dto.RiskSeriesItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GetRiskBadge {

    public static record Result(String badge, List<RiskSeriesItem> series) {}

    @Inject CheckinRepository checkinRepository;

    public Result handle(Long userId, int days) {
        if (days <= 0) days = 7;

        var last = checkinRepository.findLastN(userId, days);

        var series = toSeries(last);
        var badge = computeOverallBadge(series);

        return new Result(badge, series);
    }

    private List<RiskSeriesItem> toSeries(List<Checkin> list) {
        var out = new ArrayList<RiskSeriesItem>(list.size());
        for (var c : list) {
            RiskLevel computed = computeRisk(c.getHumor(), c.getEnergia(), c.getCargaTrabalho());
            out.add(new RiskSeriesItem(
                    c.getDataCheckin(),
                    c.getHumor(),
                    c.getEnergia(),
                    c.getCargaTrabalho(),
                    computed
            ));
        }
        return out;
    }

    private String computeOverallBadge(List<RiskSeriesItem> series) {
        if (series.isEmpty()) return RiskLevel.VERDE.name();

        long reds = series.stream()
                .filter(s -> s.getNivelRisco() == RiskLevel.VERMELHO)
                .count();

        if (reds >= Math.max(1, series.size() / 2)) return RiskLevel.VERMELHO.name();

        boolean anyYellow = series.stream()
                .anyMatch(s -> s.getNivelRisco() == RiskLevel.AMARELO);

        if (anyYellow) return RiskLevel.AMARELO.name();

        return RiskLevel.VERDE.name();
    }

    private RiskLevel computeRisk(Integer humor, Integer energia, Integer carga) {
        if (humor == null || energia == null || carga == null) return RiskLevel.VERDE;

        if (carga >= 4 && energia <= 2) return RiskLevel.VERMELHO;
        if (carga >= 3 || energia <= 3 || humor <= 2) return RiskLevel.AMARELO;

        return RiskLevel.VERDE;
    }
}
