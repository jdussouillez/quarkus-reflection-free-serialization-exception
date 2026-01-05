package com.github.jdussouillez.qrfse;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Set;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("event")
public class EventResource {

    @Inject
    protected ObjectMapper jsonMapper;

    private static final Set<Event> EVENTS = Set.of(
        new Event(1, "Devoxx France"),
        new Event(2, "JCON Europe"),
        new Event(3, "JChampionsConf")
    );

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Event get(@PathParam("id") final int id) throws EventNotFoundException {
        return EVENTS.stream()
            .filter(e -> e.id() == id)
            .findFirst()
            .orElseThrow(EventNotFoundException::new);
    }

    @ServerExceptionMapper
    public Response mapException(final EventNotFoundException ex) {
        String json = null;
        try {
            json = jsonMapper.writeValueAsString(ex);
        } catch (JsonProcessingException ex2) {
        }
        return Response.status(Response.Status.NOT_FOUND)
            .header(HttpHeaders.CONTENT_TYPE, "application/problem+json")
            .entity(json)
            .build();
    }

    public record Event(int id, String name) {
    }

    @RegisterForReflection
    @JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE
    )
    public class EventNotFoundException extends Exception {

        public EventNotFoundException() {
            super("Event not found");
        }

        @JsonProperty
        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }
}
