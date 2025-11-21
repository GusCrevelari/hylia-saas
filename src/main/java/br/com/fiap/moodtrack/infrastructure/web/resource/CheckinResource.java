package br.com.fiap.moodtrack.infrastructure.web.resource;

import br.com.fiap.moodtrack.application.usecase.CreateCheckin;
import br.com.fiap.moodtrack.application.usecase.GetRiskBadge;
import br.com.fiap.moodtrack.application.usecase.ListCheckin;
import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.domain.repository.DicaRepository;
import br.com.fiap.moodtrack.domain.repository.UsuarioRepository;
import br.com.fiap.moodtrack.infrastructure.persistence.qualifier.JpaRepo;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinOutputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.RiskResponseDto;
import br.com.fiap.moodtrack.infrastructure.web.mapper.CheckinMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class CheckinResource {

    @Inject UsuarioRepository usuarioRepo;
    @Inject @JpaRepo DicaRepository dicaRepo;

    @Inject CreateCheckin createCheckin;
    @Inject ListCheckin listCheckins;
    @Inject GetRiskBadge getRiskBadge;

    @POST
    @Path("/{userId}/checkins")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCheckin(@PathParam("userId") Long userId,
                                @Valid CheckinInputDto in,
                                @Context UriInfo uri) {

        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Dica dica = null;
        if (in.getDicaId() != null) {
            dica = dicaRepo.findById(in.getDicaId())
                    .orElseThrow(() -> new NotFoundException("Tip not found"));
        }

        Checkin toCreate = CheckinMapper.toDomain(usuario, dica, in);
        Checkin created = createCheckin.handle(toCreate);
        CheckinOutputDto out = CheckinMapper.toOutput(created);

        URI location = uri.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId()))
                .build();

        return Response.created(location).entity(out).build();
    }

    @GET
    @Path("/{userId}/checkins")
    public Response getCheckins(@PathParam("userId") Long userId,
                                @QueryParam("from") String fromStr,
                                @QueryParam("to") String toStr) {

        LocalDateTime from = null;
        LocalDateTime to = null;

        if (fromStr != null && !fromStr.isBlank()) {
            try {
                from = LocalDate.parse(fromStr).atStartOfDay();
            } catch (DateTimeParseException e) {
                throw new BadRequestException("from must be yyyy-MM-dd");
            }
        }

        if (toStr != null && !toStr.isBlank()) {
            try {
                to = LocalDate.parse(toStr).atTime(23, 59, 59);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("to must be yyyy-MM-dd");
            }
        }

        List<Checkin> list = listCheckins.handle(userId, from, to);
        List<CheckinOutputDto> out = list.stream()
                .map(CheckinMapper::toOutput)
                .collect(Collectors.toList());

        return Response.ok(out).build();
    }

    @GET
    @Path("/{userId}/risk")
    public Response getRisk(@PathParam("userId") Long userId,
                            @QueryParam("days") @DefaultValue("7") int days) {

        var result = getRiskBadge.handle(userId, days);
        RiskResponseDto dto = new RiskResponseDto(result.badge(), result.series());
        return Response.ok(dto).build();
    }
}
