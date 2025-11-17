package br.com.fiap.moodtrack.infrastructure.web.dto;

import jakarta.validation.constraints.Size;

public class ConfiguracaoInputDto {
    private Long usuarioId;
    @Size(max = 10) private String tema;
    private Integer notificacaoAtiva;
    @Size(max = 10) private String horarioLimite;
    @Size(max = 40) private String fusoHorario;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public Integer getNotificacaoAtiva() { return notificacaoAtiva; }
    public void setNotificacaoAtiva(Integer notificacaoAtiva) { this.notificacaoAtiva = notificacaoAtiva; }
    public String getHorarioLimite() { return horarioLimite; }
    public void setHorarioLimite(String horarioLimite) { this.horarioLimite = horarioLimite; }
    public String getFusoHorario() { return fusoHorario; }
    public void setFusoHorario(String fusoHorario) { this.fusoHorario = fusoHorario; }
}
