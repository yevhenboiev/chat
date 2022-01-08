package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequestDto {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, max = 32, message = "Login must have between 6 and 32 characters")
    private String login;

    @Size(min = 8, max = 20, message = "Password must have between 8 and 20 characters")
    private String password;
}
