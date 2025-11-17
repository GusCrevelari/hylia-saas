package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.infrastructure.web.dto.DicaOutputDto;

public final class DicaMapper {
    private DicaMapper() {}

    public static DicaOutputDto toOutput(Dica d) {
        var out = new DicaOutputDto();
        out.setId(d.getId());
        out.setTitulo(d.getTitulo());
        out.setDescricao(d.getDescricao());
        out.setCategoria(d.getCategoria());
        return out;
    }
}
