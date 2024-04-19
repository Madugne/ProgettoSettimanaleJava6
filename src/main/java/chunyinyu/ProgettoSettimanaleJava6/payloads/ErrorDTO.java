package chunyinyu.ProgettoSettimanaleJava6.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timestamp) {
}
