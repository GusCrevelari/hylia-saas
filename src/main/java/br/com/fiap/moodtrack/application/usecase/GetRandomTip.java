package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.repository.DicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetRandomTip {

    @Inject DicaRepository dicaRepository;

    public Dica handle() {
        return dicaRepository.findRandom().orElse(null);
    }
}
