package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.AnaliseIa;
import br.com.fiap.moodtrack.infrastructure.web.dto.AnaliseIaOutputDto;

public final class AnaliseIaMapper {
    private AnaliseIaMapper() {}

    public static AnaliseIaOutputDto toOutput(AnaliseIa a) {
        return new AnaliseIaOutputDto(
                a.getId(),
                a.getCheckin().getId(),
                a.getModeloUtilizado(),
                a.getScoreRisco(),
                a.getDataAnalise(),
                null
        );
    }
}
