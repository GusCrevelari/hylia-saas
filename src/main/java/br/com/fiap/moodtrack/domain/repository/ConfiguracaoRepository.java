package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Configuracao;
import java.util.Optional;

public interface ConfiguracaoRepository extends Repository<Configuracao, Long> {
    Optional<Configuracao> findByUsuarioId(Long usuarioId);
}
