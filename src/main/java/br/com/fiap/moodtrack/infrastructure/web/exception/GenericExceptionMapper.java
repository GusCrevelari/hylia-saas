package br.com.fiap.moodtrack.infrastructure.web.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable ex) {
        // print full stacktrace in the Quarkus console
        ex.printStackTrace();

        // find root cause (DB error, etc.)
        Throwable root = ex;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }

        Map<String, Object> details = new HashMap<>();
        details.put("exception", ex.getClass().getName());
        details.put("rootException", root.getClass().getName());
        details.put("rootMessage", String.valueOf(root.getMessage()));

        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                500,
                "Internal Server Error",
                "unexpected error",
                uriInfo != null ? uriInfo.getPath() : "",
                details
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
