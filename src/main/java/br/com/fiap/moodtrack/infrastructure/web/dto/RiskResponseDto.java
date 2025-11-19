package br.com.fiap.moodtrack.infrastructure.web.dto;

import java.util.List;

public class RiskResponseDto {
    private String badge;
    private List<RiskSeriesItem> series;

    public RiskResponseDto() {}
    public RiskResponseDto(String badge, List<RiskSeriesItem> series) {
        this.badge = badge;
        this.series = series;
    }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }
    public List<RiskSeriesItem> getSeries() { return series; }
    public void setSeries(List<RiskSeriesItem> series) { this.series = series; }
}
