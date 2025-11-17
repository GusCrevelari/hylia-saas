package br.com.fiap.moodtrack.infrastructure.web.exception;

import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

@Provider
@Singleton
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(NoSuchElementException ex) {
        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                Response.Status.NOT_FOUND.getStatusCode(),
                "Not Found",
                ex.getMessage(),
                uri != null ? uri.getPath() : null,
                null
        );
        return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(body).build();
    }
}
