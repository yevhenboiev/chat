package ru.simbirsoft.chat.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Role;
import ru.simbirsoft.chat.entity.Room;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-07T22:30:22+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientDto responseClientDto) {
        if ( responseClientDto == null ) {
            return null;
        }

        Client client = new Client();

        if ( responseClientDto.getId() != null ) {
            client.setId( responseClientDto.getId() );
        }
        client.setName( responseClientDto.getName() );
        Set<Role> set = responseClientDto.getRole();
        if ( set != null ) {
            client.setRole( new HashSet<Role>( set ) );
        }
        client.setBlock( responseClientDto.isBlock() );
        client.setStartBan( responseClientDto.getStartBan() );
        client.setEndBan( responseClientDto.getEndBan() );
        client.setClientRooms( roomDtoSetToRoomSet( responseClientDto.getClientRooms() ) );

        return client;
    }

    @Override
    public ClientDto toDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId( client.getId() );
        clientDto.setName( client.getName() );
        Set<Role> set = client.getRole();
        if ( set != null ) {
            clientDto.setRole( new HashSet<Role>( set ) );
        }
        clientDto.setBlock( client.isBlock() );
        clientDto.setStartBan( client.getStartBan() );
        clientDto.setEndBan( client.getEndBan() );
        clientDto.setClientRooms( roomSetToRoomDtoSet( client.getClientRooms() ) );

        return clientDto;
    }

    @Override
    public List<Client> allToEntity(List<ClientDto> responseClientDtoList) {
        if ( responseClientDtoList == null ) {
            return null;
        }

        List<Client> list = new ArrayList<Client>( responseClientDtoList.size() );
        for ( ClientDto clientDto : responseClientDtoList ) {
            list.add( toEntity( clientDto ) );
        }

        return list;
    }

    @Override
    public List<ClientDto> allToDTO(List<Client> clientList) {
        if ( clientList == null ) {
            return null;
        }

        List<ClientDto> list = new ArrayList<ClientDto>( clientList.size() );
        for ( Client client : clientList ) {
            list.add( toDTO( client ) );
        }

        return list;
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

    protected Room roomDtoToRoom(RoomDto roomDto) {
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

    protected Set<Room> roomDtoSetToRoomSet(Set<RoomDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Room> set1 = new HashSet<Room>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoomDto roomDto : set ) {
            set1.add( roomDtoToRoom( roomDto ) );
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

    protected RoomDto roomToRoomDto(Room room) {
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

    protected Set<RoomDto> roomSetToRoomDtoSet(Set<Room> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoomDto> set1 = new HashSet<RoomDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Room room : set ) {
            set1.add( roomToRoomDto( room ) );
        }

        return set1;
    }
}
