package br.com.fiap.moodtrack.infrastructure.web.dto;

import java.util.List;

public class RiskResponseDto {
    private String badge;
    private List<Integer> series;

    public RiskResponseDto() {}
    public RiskResponseDto(String badge, List<Integer> series) {
        this.badge = badge;
        this.series = series;
    }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }
    public List<Integer> getSeries() { return series; }
    public void setSeries(List<Integer> series) { this.series = series; }
}
