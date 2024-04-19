package chunyinyu.ProgettoSettimanaleJava6.payloads;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        @NotBlank(message = "Event title is required")
        @Size(min = 3, max = 50, message = "Event title must be between 3 and 50 characters")
        String title,
        @NotBlank(message = "Event description is required")
        String description,
        @NotBlank(message = "Event date is required")
        LocalDate date,
        @NotBlank(message = "Event date is required")
        String location,
        @NotBlank(message = "Event date is required")
        int slots
) {
}
