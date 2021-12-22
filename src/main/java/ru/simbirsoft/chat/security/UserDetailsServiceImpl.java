package ru.simbirsoft.chat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.mapper.UserDetailMapper;
import ru.simbirsoft.chat.repository.ClientRepository;

@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final UserDetailMapper userDetailMapper;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository, UserDetailMapper userDetailMapper) {
        this.clientRepository = clientRepository;
        this.userDetailMapper = userDetailMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = clientRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Client doesn't exist"));
        return userDetailMapper.toSecurityUser(client);
    }
}
