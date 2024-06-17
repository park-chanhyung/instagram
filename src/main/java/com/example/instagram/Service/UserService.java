package com.example.instagram.Service;

import com.example.instagram.Entity.SiteUser;
import com.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    public SiteUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Optional<SiteUser> getUserByUsername(String username) {
        return userRepository.findByusername(username);
    }
    public void profileEdit(SiteUser siteUser, String name, String password, MultipartFile file) throws IOException {
        siteUser.setName(name);
        siteUser.setPassword(passwordEncoder.encode(password));

//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//        UUID uuid = UUID.randomUUID();
//
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        File saveFile = new File(projectPath, fileName);
//
//        file.transferTo(saveFile);
//
//        siteUser.setFilename(fileName);
//        siteUser.setFilePath("/files/" + fileName);
//        this.userRepository.save(siteUser);
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        File directory = new File(projectPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        // URL 인코딩
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

        siteUser.setFilename(fileName);
        siteUser.setFilePath("/files/" + encodedFileName);
        this.userRepository.save(siteUser);
    }
}
