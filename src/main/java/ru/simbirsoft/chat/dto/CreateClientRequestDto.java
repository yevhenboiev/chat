package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 4, max = 32)
    private String login;

    @Size(min = 8)
    private String password;

    transient String confirmPassword;
}
