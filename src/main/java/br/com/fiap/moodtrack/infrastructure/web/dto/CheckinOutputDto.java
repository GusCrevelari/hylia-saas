package br.com.fiap.moodtrack.infrastructure.web.dto;

import java.time.LocalDateTime;

public class CheckinOutputDto {
    private Long id;
    private Long usuarioId;
    private LocalDateTime dataCheckin;
    private Integer humor;
    private Integer energia;
    private Integer cargaTrabalho;
    private String observacao;
    private String nivelRisco;
    private Long dicaId;
    private String dicaTitulo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
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
    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }
    public Long getDicaId() { return dicaId; }
    public void setDicaId(Long dicaId) { this.dicaId = dicaId; }
    public String getDicaTitulo() { return dicaTitulo; }
    public void setDicaTitulo(String dicaTitulo) { this.dicaTitulo = dicaTitulo; }
}
