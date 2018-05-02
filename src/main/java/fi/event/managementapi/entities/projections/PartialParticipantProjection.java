package fi.event.managementapi.entities.projections;


import fi.event.managementapi.entities.Participant;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "partial", types = {Participant.class})
public interface PartialParticipantProjection {
    String getName();

    Boolean getCheckedIn();
}
