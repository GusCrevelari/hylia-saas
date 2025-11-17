package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.domain.repository.FeedbackRepository;
import br.com.fiap.moodtrack.domain.repository.UsuarioRepository;
import br.com.fiap.moodtrack.infrastructure.web.dto.FeedbackInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.FeedbackOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.FeedbackMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/feedbacks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeedbackResource {

    @Inject FeedbackRepository feedbackRepo;
    @Inject UsuarioRepository usuarioRepo;

    @POST
    @Transactional
    public FeedbackOutputDto create(@Valid FeedbackInputDto in) {
        Usuario u = usuarioRepo.findById(in.getUsuarioId()).orElseThrow(NotFoundException::new);
        var f = feedbackRepo.save(FeedbackMapper.toDomain(u, in));
        return FeedbackMapper.toOutput(f);
    }

    @GET
    @Path("/users/{userId}")
    public List<FeedbackOutputDto> byUser(@PathParam("userId") Long userId) {
        return feedbackRepo.findByUsuario(userId).stream().map(FeedbackMapper::toOutput).collect(Collectors.toList());
    }
}
