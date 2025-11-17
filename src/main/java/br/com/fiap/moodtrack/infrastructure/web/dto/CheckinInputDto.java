package br.com.fiap.moodtrack.infrastructure.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class CheckinInputDto {
    private LocalDateTime dataCheckin;
    @Min(1) @Max(5) private Integer humor;
    @Min(1) @Max(5) private Integer energia;
    @Min(1) @Max(5) private Integer cargaTrabalho;
    @Size(max = 255) private String observacao;
    private Long dicaId;

    public LocalDateTime getDataCheckin() { return dataCheckin; }
    public void setDataCheckin(LocalDateTime dataCheckin) { this.dataCheckin = dataCheckin; }
    public Integer getHumor() { return humor; }
    public void setHumor(Integer humor) { this.humor = humor; }
    public Integer getEnergia() { return energia; }
    public void setEnergia(Integer energia) { this.energia = energia; }
    public Integer getCargaTrabalho() { return cargaTrabalho; }
    public void setCargaTrabalho(Integer cargaTrabalho) { this.cargaTrabalho = cargaTrabalho; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public Long getDicaId() { return dicaId; }
    public void setDicaId(Long dicaId) { this.dicaId = dicaId; }
}
