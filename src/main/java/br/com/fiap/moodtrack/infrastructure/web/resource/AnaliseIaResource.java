package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.application.usecase.RunCheckinAnalysis;
import br.com.fiap.moodtrack.domain.model.AnaliseIa;
import br.com.fiap.moodtrack.infrastructure.web.dto.AnaliseIaOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.AnaliseIaMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/users/checkins")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnaliseIaResource {

    @Inject RunCheckinAnalysis runCheckinAnalysis;

    @POST
    @Path("/{id}/analysis")
    @Transactional
    public AnaliseIaOutputDto run(@PathParam("id") Long id) {
        AnaliseIa analise = runCheckinAnalysis.handle(id);
        return AnaliseIaMapper.toOutput(analise);
    }
}
