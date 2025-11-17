package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.domain.repository.UsuarioRepository;
import br.com.fiap.moodtrack.infrastructure.web.dto.UsuarioInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.UsuarioOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.UsuarioMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject UsuarioRepository usuarioRepo;

    @POST
    @Transactional
    public Response create(@Valid UsuarioInputDto in, @Context UriInfo uri) {
        Usuario u = UsuarioMapper.toDomain(in);
        u = usuarioRepo.save(u);
        UsuarioOutputDto out = UsuarioMapper.toOutput(u);
        URI location = uri.getAbsolutePathBuilder().path(String.valueOf(u.getId())).build();
        return Response.created(location).entity(out).build();
    }

    @GET
    public List<UsuarioOutputDto> list() {
        return usuarioRepo.findAll().stream().map(UsuarioMapper::toOutput).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public UsuarioOutputDto get(@PathParam("id") Long id) {
        Usuario u = usuarioRepo.findById(id).orElseThrow(NotFoundException::new);
        return UsuarioMapper.toOutput(u);
    }
}
