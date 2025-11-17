package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Feedback;
import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackRepository extends Repository<Feedback, Long> {
    List<Feedback> findByUsuario(Long usuarioId);
    List<Feedback> findByUsuarioBetween(Long usuarioId, LocalDateTime from, LocalDateTime to);
}
