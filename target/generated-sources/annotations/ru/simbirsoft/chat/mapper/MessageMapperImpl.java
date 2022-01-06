package ru.simbirsoft.chat.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Message;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-05T14:50:32+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toEntity(MessageDto messageDto) {
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

    @Override
    public Message toEntity(CreateMessageRequestDto createMessageRequestDto) {
        if ( createMessageRequestDto == null ) {
            return null;
        }

        Message message = new Message();

        message.setContent( createMessageRequestDto.getContent() );

        return message;
    }

    @Override
    public MessageDto toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId( message.getId() );
        messageDto.setContent( message.getContent() );

        return messageDto;
    }
}
