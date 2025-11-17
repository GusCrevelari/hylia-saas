package br.com.fiap.moodtrack.infrastructure.web.exception;

import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
@Singleton
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Context UriInfo uri;

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        Map<String, Object> details = new HashMap<>();
        details.put("violations", ex.getConstraintViolations().stream()
                .map(v -> Map.of(
                        "property", property(v),
                        "invalid", v.getInvalidValue(),
                        "message", v.getMessage()))
                .collect(Collectors.toList()));

        ProblemDetails body = new ProblemDetails(
                OffsetDateTime.now(),
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Validation Failed",
                "Some fields are invalid",
                uri != null ? uri.getPath() : null,
                details
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    private String property(ConstraintViolation<?> v) {
        var path = v.getPropertyPath();
        return path != null ? path.toString() : "";
    }
}
