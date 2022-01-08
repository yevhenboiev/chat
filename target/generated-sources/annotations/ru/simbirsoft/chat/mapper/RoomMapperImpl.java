package ru.simbirsoft.chat.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.dto.UserDto;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-08T15:55:39+0300",
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
        room.setCreator( userDtoToUser( roomDto.getCreator() ) );
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
        roomDto.setCreator( userToUserDto( room.getCreator() ) );
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

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setRole( userDto.getRole() );
        user.setBlock( userDto.isBlock() );
        user.setStartBan( userDto.getStartBan() );
        user.setEndBan( userDto.getEndBan() );
        user.setUserRooms( roomDtoSetToRoomSet( userDto.getUserRooms() ) );

        return user;
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

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setRole( user.getRole() );
        userDto.setBlock( user.isBlock() );
        userDto.setStartBan( user.getStartBan() );
        userDto.setEndBan( user.getEndBan() );
        userDto.setUserRooms( roomSetToRoomDtoSet( user.getUserRooms() ) );

        return userDto;
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
