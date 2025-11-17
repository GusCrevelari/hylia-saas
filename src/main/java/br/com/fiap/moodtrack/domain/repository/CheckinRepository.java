package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Checkin;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckinRepository extends Repository<Checkin, Long> {
    Optional<Checkin> findByUsuarioAndDate(Long usuarioId, LocalDate day);
    List<Checkin> findByUsuarioBetween(Long usuarioId, LocalDateTime from, LocalDateTime to);
    List<Checkin> findLastN(Long usuarioId, int n);
}
