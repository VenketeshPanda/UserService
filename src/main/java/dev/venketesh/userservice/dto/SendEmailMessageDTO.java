package dev.venketesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailMessageDTO {
    private String from;
    private String to;
    private String subject;
    private String body;
}
