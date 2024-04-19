package chunyinyu.ProgettoSettimanaleJava6.services;

import chunyinyu.ProgettoSettimanaleJava6.entities.User;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.BadRequestException;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.RecordNotFoundException;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewUserDTO;
import chunyinyu.ProgettoSettimanaleJava6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCrypt;

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
}
