package ru.simbirsoft.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.repository.ClientRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ClientRepository clientRepository;

    @Autowired
    public Application(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("Bot");
        client.setLogin("Bot");
        client.setPassword("Bot");
        client.setActive(true);
        clientRepository.save(client);
    }
}
