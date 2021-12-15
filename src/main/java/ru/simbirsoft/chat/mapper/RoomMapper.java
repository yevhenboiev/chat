package ru.simbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toEntity(RoomDto roomDto);

    Room toEntity(CreateRoomRequestDto createRoomRequestDto);

    RoomDto toDTO(Room room);
}
