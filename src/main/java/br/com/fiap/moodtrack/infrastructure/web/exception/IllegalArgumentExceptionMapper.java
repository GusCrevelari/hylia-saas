package br.com.fiap.moodtrack.infrastructure.web.exception;

import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
@Singleton
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(IllegalArgumentException ex) {
        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                ex.getMessage(),
                uri != null ? uri.getPath() : null,
                null
        );
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(body).build();
    }
}
