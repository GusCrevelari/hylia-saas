package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
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
    @Size(max = 255)
    @Column(name = "SENHA", nullable = false, length = 255)
    private String senha;

    @Size(max = 20)
    @Column(name = "PERFIL", length = 20)
    private String perfil; // usuario | terapeuta | admin

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    // Reverse relationships (lazy, no cascade by default)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Checkin> checkins;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Configuracao configuracao;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    @PrePersist
    public void prePersist() {
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public List<Checkin> getCheckins() { return checkins; }
    public void setCheckins(List<Checkin> checkins) { this.checkins = checkins; }

    public List<Feedback> getFeedbacks() { return feedbacks; }
    public void setFeedbacks(List<Feedback> feedbacks) { this.feedbacks = feedbacks; }

    public Configuracao getConfiguracao() { return configuracao; }
    public void setConfiguracao(Configuracao configuracao) { this.configuracao = configuracao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", email='" + email + "'}";
    }
}
