package br.com.fiap.moodtrack.infrastructure.web.exception;

import java.time.OffsetDateTime;
import java.util.Map;

public final class ProblemDetails {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, Object> details;

    public ProblemDetails() {}
    public ProblemDetails(OffsetDateTime timestamp, int status, String error, String message, String path, Map<String, Object> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
}
