package ru.simbirsoft.chat.mapper;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-16T00:10:48+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room toEntity(RoomDto roomDto) {
        if ( roomDto == null ) {
            return null;
        }

        Room room = new Room();

        if ( roomDto.getId() != null ) {
            room.setId( roomDto.getId() );
        }
        room.setRoomName( roomDto.getRoomName() );
        room.setPrivate( roomDto.isPrivate() );
        room.setClientList( messageDtoSetToClientSet( roomDto.getClientList() ) );

        return room;
    }

    @Override
    public Room toEntity(CreateRoomRequestDto createRoomRequestDto) {
        if ( createRoomRequestDto == null ) {
            return null;
        }

        Room room = new Room();

        room.setRoomName( createRoomRequestDto.getRoomName() );
        room.setPrivate( createRoomRequestDto.isPrivate() );
        room.setClientList( messageDtoSetToClientSet( createRoomRequestDto.getClientList() ) );

        return room;
    }

    @Override
    public RoomDto toDTO(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setId( room.getId() );
        roomDto.setRoomName( room.getRoomName() );
        roomDto.setPrivate( room.isPrivate() );
        roomDto.setClientList( clientSetToMessageDtoSet( room.getClientList() ) );

        return roomDto;
    }

    protected Client messageDtoToClient(MessageDto messageDto) {
        if ( messageDto == null ) {
            return null;
        }

        Client client = new Client();

        if ( messageDto.getId() != null ) {
            client.setId( messageDto.getId() );
        }

        return client;
    }

    protected Set<Client> messageDtoSetToClientSet(Set<MessageDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Client> set1 = new HashSet<Client>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MessageDto messageDto : set ) {
            set1.add( messageDtoToClient( messageDto ) );
        }

        return set1;
    }

    protected MessageDto clientToMessageDto(Client client) {
        if ( client == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId( client.getId() );

        return messageDto;
    }

    protected Set<MessageDto> clientSetToMessageDtoSet(Set<Client> set) {
        if ( set == null ) {
            return null;
        }

        Set<MessageDto> set1 = new HashSet<MessageDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Client client : set ) {
            set1.add( clientToMessageDto( client ) );
        }

        return set1;
    }
}
