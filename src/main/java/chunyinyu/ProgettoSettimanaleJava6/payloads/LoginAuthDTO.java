package chunyinyu.ProgettoSettimanaleJava6.payloads;

import jakarta.validation.constraints.NotBlank;

public record LoginAuthDTO(
        @NotBlank
        String usernameOrEmail,
        @NotBlank
        String password) {
}