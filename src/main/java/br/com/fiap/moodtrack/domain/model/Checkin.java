package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CHECKINS")
public class Checkin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHECKIN")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(name = "DATA_CHECKIN", nullable = false)
    private LocalDateTime dataCheckin;

    @Min(1) @Max(5)
    @Column(name = "HUMOR")
    private Integer humor;

    @Min(1) @Max(5)
    @Column(name = "ENERGIA")
    private Integer energia;

    @Min(1) @Max(5)
    @Column(name = "CARGA_TRABALHO")
    private Integer cargaTrabalho;

    @Size(max = 255)
    @Column(name = "OBSERVACAO", length = 255)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "NIVEL_RISCO", length = 10)
    private RiskLevel nivelRisco; // VERDE | AMARELO | VERMELHO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICA")
    private Dica dica; // opcional — dica sugerida no dia

    // reverse relationship (opcional; compila quando AnaliseIa existir)
    @OneToMany(mappedBy = "checkin", fetch = FetchType.LAZY)
    private List<AnaliseIa> analises;

    public Checkin() {}

    public Checkin(Usuario usuario, LocalDateTime dataCheckin,
                   Integer humor, Integer energia, Integer cargaTrabalho,
                   String observacao, RiskLevel nivelRisco, Dica dica) {
        this.usuario = usuario;
        this.dataCheckin = dataCheckin;
        this.humor = humor;
        this.energia = energia;
        this.cargaTrabalho = cargaTrabalho;
        this.observacao = observacao;
        this.nivelRisco = nivelRisco;
        this.dica = dica;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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

    public RiskLevel getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(RiskLevel nivelRisco) { this.nivelRisco = nivelRisco; }

    public Dica getDica() { return dica; }
    public void setDica(Dica dica) { this.dica = dica; }

    public List<AnaliseIa> getAnalises() { return analises; }
    public void setAnalises(List<AnaliseIa> analises) { this.analises = analises; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkin other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "Checkin{id=" + id + ", usuario=" + (usuario != null ? usuario.getId() : null) + "}";
    }
}
