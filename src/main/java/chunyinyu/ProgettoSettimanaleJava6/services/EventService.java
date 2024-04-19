package chunyinyu.ProgettoSettimanaleJava6.services;

import chunyinyu.ProgettoSettimanaleJava6.entities.Event;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.RecordNotFoundException;
import chunyinyu.ProgettoSettimanaleJava6.payloads.EventDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewEventDTO;
import chunyinyu.ProgettoSettimanaleJava6.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Page<Event> getEvents(int page, int size, String sortBy){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventRepository.findAll(pageable);
    }

    public Event addEvent(NewEventDTO body) {
        return eventRepository.save(new Event(body.title(), body.description(), body.date(), body.location(), body.slots()));
    }

    public Page<Event> getEvent(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }


    public Event findById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Device", id));
    }

    public Event updateEvent(UUID id, EventDTO body) {
        Event event = this.findById(id);
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setLocation(body.location());
        event.setSlots(body.slots());
        return eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        Event event = this.findById(id);
        eventRepository.delete(event);
    }

    public List<Event> findEventByUser(UUID userId) {
        return eventRepository.findAllByUsersId(userId);
    }
}
