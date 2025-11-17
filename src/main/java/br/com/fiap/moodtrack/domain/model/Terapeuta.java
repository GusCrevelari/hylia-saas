package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "TERAPEUTAS")
public class Terapeuta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TERAPEUTA")
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(name = "NOME", nullable = false, length = 80)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 120)
    @Column(name = "EMAIL", nullable = false, length = 120, unique = true)
    private String email;

    @NotBlank
    @Size(max = 30)
    @Column(name = "RNTP", nullable = false, length = 30)
    private String rntp; // Registro Nacional de Terapeuta Profissional

    public Terapeuta() {}

    public Terapeuta(String nome, String email, String rntp) {
        this.nome = nome;
        this.email = email;
        this.rntp = rntp;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRntp() { return rntp; }
    public void setRntp(String rntp) { this.rntp = rntp; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Terapeuta other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Terapeuta{id=" + id + ", email='" + email + "'}";
    }
}
