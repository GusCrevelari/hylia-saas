package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.infrastructure.web.dto.UsuarioInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.UsuarioOutputDto;

public final class UsuarioMapper {
    private UsuarioMapper() {}

    public static Usuario toDomain(UsuarioInputDto in) {
        var u = new Usuario();
        u.setNome(in.getNome());
        u.setEmail(in.getEmail());
        u.setSenha(in.getSenha());
        u.setPerfil(in.getPerfil());
        return u;
    }

    public static UsuarioOutputDto toOutput(Usuario u) {
        var out = new UsuarioOutputDto();
        out.setId(u.getId());
        out.setNome(u.getNome());
        out.setEmail(u.getEmail());
        out.setPerfil(u.getPerfil());
        return out;
    }
}
