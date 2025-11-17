package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Dica;
import java.util.Optional;

public interface DicaRepository extends Repository<Dica, Long> {
    Optional<Dica> findRandom();
}
