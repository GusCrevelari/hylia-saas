package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "CONFIGURACOES")
public class Configuracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONFIG")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false, unique = true)
    private Usuario usuario;

    @Size(max = 10)
    @Column(name = "TEMA", length = 10)
    private String tema; // e.g., "claro" | "escuro"

    // Stored as 0/1 in Oracle (NUMBER(1)); keep as Integer for simplicity
    @Column(name = "NOTIFICACAO_ATV")
    private Integer notificacaoAtiva; // 0 = off, 1 = on

    @Size(max = 10)
    @Column(name = "HORARIO_LIMITE", length = 10)
    private String horarioLimite; // e.g., "21:00"

    @Size(max = 40)
    @Column(name = "FUSO_HORARIO", length = 40)
    private String fusoHorario; // e.g., "America/Sao_Paulo"

    public Configuracao() {}

    public Configuracao(Usuario usuario, String tema, Integer notificacaoAtiva,
                        String horarioLimite, String fusoHorario) {
        this.usuario = usuario;
        this.tema = tema;
        this.notificacaoAtiva = notificacaoAtiva;
        this.horarioLimite = horarioLimite;
        this.fusoHorario = fusoHorario;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public Integer getNotificacaoAtiva() { return notificacaoAtiva; }
    public void setNotificacaoAtiva(Integer notificacaoAtiva) { this.notificacaoAtiva = notificacaoAtiva; }

    public String getHorarioLimite() { return horarioLimite; }
    public void setHorarioLimite(String horarioLimite) { this.horarioLimite = horarioLimite; }

    public String getFusoHorario() { return fusoHorario; }
    public void setFusoHorario(String fusoHorario) { this.fusoHorario = fusoHorario; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuracao other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Configuracao{id=" + id + ", usuario=" + (usuario != null ? usuario.getId() : null) + "}";
    }
}
