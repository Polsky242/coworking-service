package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.polskiy.dao.UserDao;

@Service
@RequiredArgsConstructor
public class CoworkingUserDetails implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userDao.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
                ("user with login %s not found".formatted(username))
        ));
    }
}
