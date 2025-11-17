package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.RiskLevel;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GetRiskBadge {

    public static record Result(String badge, List<Integer> series) {}

    @Inject CheckinRepository checkinRepository;

    public Result handle(Long userId, int days) {
        if (days <= 0) days = 7;
        var last = checkinRepository.findLastN(userId, days);
        var series = toSeries(last);
        var badge = compute(series);
        return new Result(badge, series);
    }

    private List<Integer> toSeries(List<Checkin> list) {
        var out = new ArrayList<Integer>(list.size());
        for (var c : list) {
            Integer v = c.getCargaTrabalho();
            if (v == null) v = 0;
            out.add(v);
        }
        return out;
    }

    private String compute(List<Integer> cargaSeries) {
        if (cargaSeries.isEmpty()) return RiskLevel.VERDE.name();
        int high = 0;
        for (Integer v : cargaSeries) if (v != null && v >= 4) high++;
        if (high >= Math.max(1, cargaSeries.size() / 2)) return RiskLevel.VERMELHO.name();
        for (Integer v : cargaSeries) if (v != null && v >= 3) return RiskLevel.AMARELO.name();
        return RiskLevel.VERDE.name();
    }
}
