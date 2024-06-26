package chunyinyu.ProgettoSettimanaleJava6.services;

import chunyinyu.ProgettoSettimanaleJava6.entities.Event;
import chunyinyu.ProgettoSettimanaleJava6.entities.User;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.BadRequestException;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.RecordNotFoundException;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewUserDTO;
import chunyinyu.ProgettoSettimanaleJava6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCrypt;
    @Autowired
    private EventService eventService;

    public Page<User> getUsers(int page, int size, String sortBy){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public User save(NewUserDTO body) throws BadRequestException{
        this.userRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email " + user.getEmail() + " already in use");
                }
        );
        User newUser = new User(body.firstName(), body.lastName(), body.email(), bCrypt.encode(body.password()));
        return userRepository.save(newUser);
    }

    public User findById(UUID userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(userId.toString()));
    }

    public User findByIdAndUpdate(UUID userId, User modifiedUser){
        User found = this.findById(userId);
        found.setFirstName(modifiedUser.getFirstName());
        found.setLastName(modifiedUser.getLastName());
        found.setEmail(modifiedUser.getEmail());
        found.setPassword(modifiedUser.getPassword());
        return this.userRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId){
        User found = this.findById(userId);
        this.userRepository.delete(found);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("User with email: " + email + " not found!"));
    }

    public User assignUserToEvent(UUID id, UUID eventId) {
        User user = this.findById(id);
        Event event = eventService.findById(eventId);
        List<Event> eventsList = user.getEvents();
        eventsList.add(event);
        user.setEvents(eventsList);
        return userRepository.save(user);
    }

//    public Page<User> getEventsByUser(Event event, Pageable pageable) {
//        return userRepository.findAllByEvent(event, pageable);
//    }
}
