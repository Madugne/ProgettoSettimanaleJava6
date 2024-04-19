package chunyinyu.ProgettoSettimanaleJava6.controllers;

import chunyinyu.ProgettoSettimanaleJava6.entities.Event;
import chunyinyu.ProgettoSettimanaleJava6.payloads.EventDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewEventDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewEventRespDTO;
import chunyinyu.ProgettoSettimanaleJava6.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventService.getEvents(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HOST')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventRespDTO saveEvent(@RequestBody NewEventDTO body){
        return new NewEventRespDTO(eventService.addEvent(body).getId());
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId){
        return eventService.findById(eventId);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('HOST')")
    public Event update(@PathVariable UUID deviceId, @RequestBody EventDTO body){
        return eventService.updateEvent(deviceId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('HOST')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID deviceId){
        eventService.deleteEvent(deviceId);
    }

}
