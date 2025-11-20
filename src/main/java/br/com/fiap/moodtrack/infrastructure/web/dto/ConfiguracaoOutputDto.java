package br.com.fiap.moodtrack.infrastructure.web.dto;

public class ConfiguracaoOutputDto {
    private Long id;
    private Long usuarioId;
    private String tema;
    private Boolean notificacaoAtiva;
    private String horarioLimite;
    private String fusoHorario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
