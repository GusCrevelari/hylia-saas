package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ListCheckin {

    @Inject CheckinRepository checkinRepository;

    public List<Checkin> handle(Long userId, LocalDateTime from, LocalDateTime to) {
        if (from == null && to == null) return checkinRepository.findLastN(userId, 30);
        if (from == null) from = LocalDateTime.MIN;
        if (to == null) to = LocalDateTime.MAX;
        return checkinRepository.findByUsuarioBetween(userId, from, to);
    }
}
