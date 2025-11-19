package br.com.fiap.moodtrack.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ConfiguracaoInputDto {

    private Long usuarioId;

    @Size(max = 10)
    private String tema;

    @NotNull
    private Boolean notificacaoAtiva;   // ✅ Boolean instead of Integer

    @Size(max = 10)
    private String horarioLimite;

    @Size(max = 40)
    private String fusoHorario;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public Boolean getNotificacaoAtiva() { return notificacaoAtiva; }
    public void setNotificacaoAtiva(Boolean notificacaoAtiva) { this.notificacaoAtiva = notificacaoAtiva; }

    public String getHorarioLimite() { return horarioLimite; }
    public void setHorarioLimite(String horarioLimite) { this.horarioLimite = horarioLimite; }

    public String getFusoHorario() { return fusoHorario; }
    public void setFusoHorario(String fusoHorario) { this.fusoHorario = fusoHorario; }
}
