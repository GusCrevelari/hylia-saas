package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.Feedback;
import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.infrastructure.web.dto.FeedbackInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.FeedbackOutputDto;

import java.time.LocalDateTime;

public final class FeedbackMapper {
    private FeedbackMapper() {}

    public static Feedback toDomain(Usuario usuario, FeedbackInputDto in) {
        var f = new Feedback();
        f.setUsuario(usuario);
        f.setDataFeedback(in.getDataFeedback() != null ? in.getDataFeedback() : LocalDateTime.now());
        f.setAvaliacao(in.getAvaliacao());
        f.setComentario(in.getComentario());
        return f;
    }

    public static FeedbackOutputDto toOutput(Feedback f) {
        var out = new FeedbackOutputDto();
        out.setId(f.getId());
        out.setUsuarioId(f.getUsuario() != null ? f.getUsuario().getId() : null);
        out.setDataFeedback(f.getDataFeedback());
        out.setAvaliacao(f.getAvaliacao());
        out.setComentario(f.getComentario());
        return out;
    }
}
