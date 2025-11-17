package br.com.fiap.moodtrack.infrastructure.web.exception;

import br.com.fiap.moodtrack.application.exception.ResourceNotFoundException;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
@Singleton
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(ResourceNotFoundException ex) {
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
