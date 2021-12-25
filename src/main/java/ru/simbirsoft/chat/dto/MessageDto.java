package ru.simbirsoft.chat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    @NotNull
    private Long clientId;
    @NotNull
    private Long roomId;
    @NotNull
    private Timestamp time;
    @NotBlank
    @Size(max = 1024)
    private String content;
}
