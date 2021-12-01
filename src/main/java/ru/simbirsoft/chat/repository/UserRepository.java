package ru.simbirsoft.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat.entity.Client;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {
}
