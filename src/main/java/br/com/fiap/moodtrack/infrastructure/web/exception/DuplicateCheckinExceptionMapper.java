package br.com.fiap.moodtrack.infrastructure.web.exception;

import br.com.fiap.moodtrack.application.exception.DuplicateCheckinException;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
@Singleton
public class DuplicateCheckinExceptionMapper implements ExceptionMapper<DuplicateCheckinException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(DuplicateCheckinException ex) {
        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                Response.Status.CONFLICT.getStatusCode(),
                "Conflict",
                ex.getMessage(),
                uri != null ? uri.getPath() : null,
                null
        );
        return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity(body).build();
    }
}
