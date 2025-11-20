package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.application.exception.ResourceNotFoundException;
import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.domain.repository.ConfiguracaoRepository;
import br.com.fiap.moodtrack.domain.repository.UsuarioRepository;
import br.com.fiap.moodtrack.infrastructure.web.dto.ConfiguracaoInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.ConfiguracaoOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.ConfiguracaoMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfiguracaoResource {

    @Inject ConfiguracaoRepository configRepo;
    @Inject UsuarioRepository usuarioRepo;

    @GET
    @Path("/users/{userId}")
    public ConfiguracaoOutputDto getByUser(@PathParam("userId") Long userId) {
        var cfg = configRepo.findByUsuarioId(userId)
                .orElseThrow(() -> new NotFoundException("config not found for user"));
        return ConfiguracaoMapper.toOutput(cfg);
    }

    @POST
    @Transactional
    public ConfiguracaoOutputDto upsert(@Valid ConfiguracaoInputDto in) {
        Usuario u = usuarioRepo.findById(in.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found: " + in.getUsuarioId()));

        var existing = configRepo.findByUsuarioId(u.getId()).orElse(null);
        if (existing == null) {
            var created = configRepo.save(ConfiguracaoMapper.toDomain(u, in));
            return ConfiguracaoMapper.toOutput(created);
        } else {
            existing.setTema(in.getTema());
            existing.setNotificacaoAtiva(in.getNotificacaoAtiva() ? 1 : 0);
            existing.setHorarioLimite(in.getHorarioLimite());
            existing.setFusoHorario(in.getFusoHorario());
            return ConfiguracaoMapper.toOutput(configRepo.save(existing));
        }
    }
}
