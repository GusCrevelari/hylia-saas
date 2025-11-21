package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.application.usecase.DeleteCheckin;
import br.com.fiap.moodtrack.application.usecase.UpdateCheckin;
import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.CheckinMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/checkins")              // <<< raiz direta
@Produces(MediaType.APPLICATION_JSON)
public class CheckinCrudResource {

    @Inject
    UpdateCheckin updateCheckin;

    @Inject
    DeleteCheckin deleteCheckin;

    // PUT /checkins/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putCheckin(@PathParam("id") Long id,
                               @Valid CheckinInputDto in) {

        Checkin updated = updateCheckin.handle(
                id,
                in.getHumor(),
                in.getEnergia(),
                in.getCargaTrabalho(),
                in.getObservacao(),
                in.getDicaId(),
                in.getDataCheckin()
        );
        return Response.ok(CheckinMapper.toOutput(updated)).build();
    }

    // DELETE /checkins/{id}
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCheckin(@PathParam("id") Long id) {
        deleteCheckin.handle(id);
        return Response.noContent().build();
    }
}
