package br.com.fiap.moodtrack.domain.repository;

import br.com.fiap.moodtrack.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends Repository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
