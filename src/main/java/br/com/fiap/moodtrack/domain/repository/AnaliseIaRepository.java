package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.AnaliseIa;
import java.util.List;

public interface AnaliseIaRepository extends Repository<AnaliseIa, Long> {
    List<AnaliseIa> findByCheckin(Long checkinId);
}
