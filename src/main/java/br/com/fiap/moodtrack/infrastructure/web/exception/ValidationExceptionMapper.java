package br.com.fiap.moodtrack.infrastructure.web.exception;

import br.com.fiap.moodtrack.application.exception.ValidationException;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
@Singleton
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(ValidationException ex) {
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
