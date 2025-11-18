package br.com.fiap.moodtrack.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;

@Entity
@Table(name = "ANALISES_IA")
public class AnaliseIa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ANALISE")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CHECKIN", nullable = false)
    private Checkin checkin;

    @NotBlank
    @Size(max = 50)
    @Column(name = "MODELO_UTILIZADO", nullable = false, length = 50)
    private String modeloUtilizado;

    // NUMBER(precision, scale) in Oracle; map as Double
    // Validate bounds in use cases if needed (e.g., 0.0..1.0)
    @Column(name = "SCORE_RISCO", columnDefinition = "NUMBER")
    private Double scoreRisco;

    @Column(name = "DATA_ANALISE")
    private LocalDateTime dataAnalise;

    public AnaliseIa() {}

    public AnaliseIa(Checkin checkin, String modeloUtilizado, Double scoreRisco, LocalDateTime dataAnalise) {
        this.checkin = checkin;
        this.modeloUtilizado = modeloUtilizado;
        this.scoreRisco = scoreRisco;
        this.dataAnalise = dataAnalise;
    }

    @PrePersist
    public void prePersist() {
        if (this.dataAnalise == null) {
            this.dataAnalise = LocalDateTime.now();
        }
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Checkin getCheckin() { return checkin; }
    public void setCheckin(Checkin checkin) { this.checkin = checkin; }

    public String getModeloUtilizado() { return modeloUtilizado; }
    public void setModeloUtilizado(String modeloUtilizado) { this.modeloUtilizado = modeloUtilizado; }

    public Double getScoreRisco() { return scoreRisco; }
    public void setScoreRisco(Double scoreRisco) { this.scoreRisco = scoreRisco; }

    public LocalDateTime getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDateTime dataAnalise) { this.dataAnalise = dataAnalise; }

    // equals & hashCode by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnaliseIa other)) return false;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

    @Override
    public String toString() {
        return "AnaliseIa{id=" + id + ", checkin=" + (checkin != null ? checkin.getId() : null) + "}";
    }
}
