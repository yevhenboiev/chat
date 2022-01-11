package ru.simbirsoft.chat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.chat.entity.enums.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    @NotBlank
    private String name;
    @JsonIgnore
    private String login;
    @JsonIgnore
    private String password;
    @NotNull
    private Role role;
    @NotNull
    private boolean isBlock;
    private boolean isActive;
    private Timestamp startBan;
    private Timestamp endBan;
    private Set<Long> clientRooms;
}
