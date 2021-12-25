package ru.simbirsoft.chat.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-25T23:33:31+0300",
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
        room.setCreator( clientDtoToClient( roomDto.getCreator() ) );
        room.setPrivate( roomDto.isPrivate() );
        room.setMessages( messageDtoListToMessageList( roomDto.getMessages() ) );

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
        roomDto.setCreator( clientToClientDto( room.getCreator() ) );
        roomDto.setPrivate( room.isPrivate() );
        roomDto.setMessages( messageListToMessageDtoList( room.getMessages() ) );

        return roomDto;
    }

    protected Set<Room> roomDtoSetToRoomSet(Set<RoomDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Room> set1 = new HashSet<Room>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoomDto roomDto : set ) {
            set1.add( toEntity( roomDto ) );
        }

        return set1;
    }

    protected Client clientDtoToClient(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        if ( clientDto.getId() != null ) {
            client.setId( clientDto.getId() );
        }
        client.setName( clientDto.getName() );
        client.setRole( clientDto.getRole() );
        client.setBlock( clientDto.isBlock() );
        client.setStartBan( clientDto.getStartBan() );
        client.setEndBan( clientDto.getEndBan() );
        client.setClientRooms( roomDtoSetToRoomSet( clientDto.getClientRooms() ) );

        return client;
    }

    protected Message messageDtoToMessage(MessageDto messageDto) {
        if ( messageDto == null ) {
            return null;
        }

        Message message = new Message();

        if ( messageDto.getId() != null ) {
            message.setId( messageDto.getId() );
        }
        message.setContent( messageDto.getContent() );

        return message;
    }

    protected List<Message> messageDtoListToMessageList(List<MessageDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Message> list1 = new ArrayList<Message>( list.size() );
        for ( MessageDto messageDto : list ) {
            list1.add( messageDtoToMessage( messageDto ) );
        }

        return list1;
    }

    protected Set<RoomDto> roomSetToRoomDtoSet(Set<Room> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoomDto> set1 = new HashSet<RoomDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Room room : set ) {
            set1.add( toDTO( room ) );
        }

        return set1;
    }

    protected ClientDto clientToClientDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId( client.getId() );
        clientDto.setName( client.getName() );
        clientDto.setRole( client.getRole() );
        clientDto.setBlock( client.isBlock() );
        clientDto.setStartBan( client.getStartBan() );
        clientDto.setEndBan( client.getEndBan() );
        clientDto.setClientRooms( roomSetToRoomDtoSet( client.getClientRooms() ) );

        return clientDto;
    }

    protected MessageDto messageToMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId( message.getId() );
        messageDto.setContent( message.getContent() );

        return messageDto;
    }

    protected List<MessageDto> messageListToMessageDtoList(List<Message> list) {
        if ( list == null ) {
            return null;
        }

        List<MessageDto> list1 = new ArrayList<MessageDto>( list.size() );
        for ( Message message : list ) {
            list1.add( messageToMessageDto( message ) );
        }

        return list1;
    }
}
