package br.com.fiap.moodtrack.infrastructure.web.exception;

import jakarta.inject.Singleton;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;

@Provider
@Singleton
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(Throwable ex) {
        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal Server Error",
                "unexpected error",
                uri != null ? uri.getPath() : null,
                null
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(body).build();
    }
}
