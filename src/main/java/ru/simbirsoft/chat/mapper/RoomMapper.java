package ru.simbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toEntity(RoomDto roomDto);

    Room toEntity(CreateRoomRequestDto createRoomRequestDto);

    RoomDto toDTO(Room room);
}
