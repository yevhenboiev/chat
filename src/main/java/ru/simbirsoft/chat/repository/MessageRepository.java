package ru.simbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.chat.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
