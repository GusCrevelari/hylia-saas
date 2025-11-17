package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DICAS")
public class Dica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DICA")
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(name = "TITULO", nullable = false, length = 80)
    private String titulo;

    @NotBlank
    @Size(max = 255)
    @Column(name = "DESCRICAO", nullable = false, length = 255)
    private String descricao;

    @NotBlank
    @Size(max = 40)
    @Column(name = "CATEGORIA", nullable = false, length = 40)
    private String categoria;

    // reverse (optional). will compile once Checkin exists; ok to keep red for now.
    @OneToMany(mappedBy = "dica", fetch = FetchType.LAZY)
    private List<Checkin> checkins;

    public Dica() {}

    public Dica(String titulo, String descricao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<Checkin> getCheckins() { return checkins; }
    public void setCheckins(List<Checkin> checkins) { this.checkins = checkins; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dica other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Dica{id=" + id + ", titulo='" + titulo + "'}";
    }
}
