package br.com.fiap.moodtrack.infrastructure.web.dto;

import br.com.fiap.moodtrack.domain.model.RiskLevel;
import java.time.LocalDateTime;

public class RiskSeriesItem {
    private LocalDateTime date;
    private Integer humor;
    private Integer energia;
    private Integer cargaTrabalho;
    private RiskLevel nivelRisco;

    public RiskSeriesItem() {}

    public RiskSeriesItem(LocalDateTime date, Integer humor, Integer energia, Integer cargaTrabalho, RiskLevel nivelRisco) {
        this.date = date;
        this.humor = humor;
        this.energia = energia;
        this.cargaTrabalho = cargaTrabalho;
        this.nivelRisco = nivelRisco;
    }

    public LocalDateTime getDate() { return date; }
    public Integer getHumor() { return humor; }
    public Integer getEnergia() { return energia; }
    public Integer getCargaTrabalho() { return cargaTrabalho; }
    public RiskLevel getNivelRisco() { return nivelRisco; }
}
