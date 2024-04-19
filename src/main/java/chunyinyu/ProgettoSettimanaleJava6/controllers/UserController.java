package chunyinyu.ProgettoSettimanaleJava6.controllers;

import chunyinyu.ProgettoSettimanaleJava6.entities.User;
import chunyinyu.ProgettoSettimanaleJava6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('HOST')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUsers(page, size, sortBy);
    }


    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User updatedUser){
        return this.userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }
    @GetMapping("/{userId}")
    public User findById(@PathVariable UUID userId){
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('HOST')")
    public User update(@PathVariable UUID userId, @RequestBody User body){
        return userService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('HOST')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId){
        userService.findByIdAndDelete(userId);
    }

    @PutMapping("/assignEvent/{userId}/{eventId}")
    @PreAuthorize("hasAuthority('HOST')")
    public void assignEvent(@PathVariable UUID userId, @PathVariable UUID eventId){
        userService.assignUserToEvent(userId, eventId);
    }

}
