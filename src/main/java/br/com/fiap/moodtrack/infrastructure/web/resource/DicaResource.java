package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.application.usecase.GetRandomTip;
import br.com.fiap.moodtrack.infrastructure.web.dto.DicaOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.DicaMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/tips")
@Produces(MediaType.APPLICATION_JSON)
public class DicaResource {

    @Inject GetRandomTip getRandomTip;

    @GET
    @Path("/random")
    public DicaOutputDto random() {
        var d = getRandomTip.handle();
        if (d == null) throw new NotFoundException("no tips available");
        return DicaMapper.toOutput(d);
    }
}
