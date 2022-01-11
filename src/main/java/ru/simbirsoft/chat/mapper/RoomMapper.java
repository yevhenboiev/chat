package ru.simbirsoft.chat.mapper;

import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Room;

//@Mapper(componentModel = "spring", imports = ClientRepository.class)
public interface RoomMapper {
//
//    @Mapping(target = "creator", ignore = true)
    Room toEntity(RoomDto roomDto);

    Room toEntity(CreateRoomRequestDto createRoomRequestDto);

//    @Mappings( @Mapping(target= "creator",  expression = "java(room.getCreator.getId())"))
    RoomDto toDTO(Room room);
}
