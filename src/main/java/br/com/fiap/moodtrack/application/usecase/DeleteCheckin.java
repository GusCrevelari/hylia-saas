package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.application.exception.ResourceNotFoundException;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteCheckin {

    @Inject CheckinRepository checkinRepository;

    @Transactional
    public void handle(Long id) {
        if (!checkinRepository.existsById(id)) throw new ResourceNotFoundException("checkin not found: " + id);
        checkinRepository.deleteById(id);
    }
}
