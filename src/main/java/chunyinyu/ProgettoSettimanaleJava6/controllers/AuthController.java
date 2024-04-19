package chunyinyu.ProgettoSettimanaleJava6.controllers;

import chunyinyu.ProgettoSettimanaleJava6.entities.User;
import chunyinyu.ProgettoSettimanaleJava6.exceptions.BadRequestException;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewUserDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.NewUserRespDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.UserLoginDTO;
import chunyinyu.ProgettoSettimanaleJava6.payloads.UserLoginResponseDTO;
import chunyinyu.ProgettoSettimanaleJava6.services.AuthService;
import chunyinyu.ProgettoSettimanaleJava6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload){
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserRespDTO(this.userService.save(body).getId());
    }
}
