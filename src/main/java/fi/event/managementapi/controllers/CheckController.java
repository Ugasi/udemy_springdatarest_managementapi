package fi.event.managementapi.controllers;

import fi.event.managementapi.controllers.exceptions.AlreadyCheckedInException;
import fi.event.managementapi.controllers.exceptions.NotCheckedInException;
import fi.event.managementapi.entities.Participant;
import fi.event.managementapi.repos.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/events")
public class CheckController {

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/checkin/{id}")
    public ResponseEntity<PersistentEntityResource> checkIn(@PathVariable Long id, PersistentEntityResourceAssembler assembler) {
        Participant participant = participantRepository.findById(id).orElse(null);
        if (participant != null) {
            if (participant.getCheckedIn()) {
                throw new AlreadyCheckedInException();
            }
            participant.setCheckedIn(true);
            participantRepository.save(participant);
        }
        return ResponseEntity.ok(assembler.toResource(participant));
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<PersistentEntityResource> checkOut(@PathVariable Long id, PersistentEntityResourceAssembler assembler) {
        Participant participant = participantRepository.findById(id).orElse(null);
        if (participant != null) {
            if (!participant.getCheckedIn()) {
                throw new NotCheckedInException();
            }
            participant.setCheckedIn(false);
            participantRepository.save(participant);
        }
        return ResponseEntity.ok(assembler.toResource(participant));
    }

}
