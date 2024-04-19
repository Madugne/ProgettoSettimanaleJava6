package chunyinyu.ProgettoSettimanaleJava6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotBlank(message = "First name is required")
                      @Size(min = 3, message = "First name (firstName) must be at least 3 characters")
                      String firstName,
                      @NotBlank(message = "Last name is required")
                      @Size(min = 2, message = "Last name must be at least 2 characters")
                      String lastName,
                      @NotBlank(message = "Email is required")
                      @Email(message = "Email must be a valid email address")
                      String email,
                      @NotBlank
                      @Size(min = 8, message = "Password must be at least 8 characters long")
                      String password
 ) {
}
