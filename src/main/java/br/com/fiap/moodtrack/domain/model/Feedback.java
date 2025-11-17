package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "FEEDBACKS")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FEEDBACK")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "DATA_FEEDBACK")
    private LocalDateTime dataFeedback;

    @Min(1) @Max(5)
    @Column(name = "AVALIACAO")
    private Integer avaliacao; // 1..5

    @Size(max = 255)
    @Column(name = "COMENTARIO", length = 255)
    private String comentario;

    public Feedback() {}

    public Feedback(Usuario usuario, LocalDateTime dataFeedback, Integer avaliacao, String comentario) {
        this.usuario = usuario;
        this.dataFeedback = dataFeedback;
        this.avaliacao = avaliacao;
        this.comentario = comentario;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getDataFeedback() { return dataFeedback; }
    public void setDataFeedback(LocalDateTime dataFeedback) { this.dataFeedback = dataFeedback; }

    public Integer getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Feedback{id=" + id + ", usuario=" + (usuario != null ? usuario.getId() : null) + "}";
    }
}
