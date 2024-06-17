package com.example.instagram.Service;

import com.example.instagram.Entity.SiteUser;
import com.example.instagram.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SiteUser create(String username,String name,String password){
        SiteUser siteUser=new SiteUser();

        siteUser.setUsername(username);
        siteUser.setName(name);
        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setCreateTime(LocalDateTime.now());
        this.userRepository.save(siteUser);
        return siteUser;
    }

}
