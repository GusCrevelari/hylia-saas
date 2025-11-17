package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Terapeuta;
import java.util.Optional;

public interface TerapeutaRepository extends Repository<Terapeuta, Long> {
    Optional<Terapeuta> findByEmail(String email);
    Optional<Terapeuta> findByRntp(String rntp);
}
