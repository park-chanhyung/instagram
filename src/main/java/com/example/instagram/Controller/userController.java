package com.example.instagram.Controller;

import com.example.instagram.Entity.SiteUser;
import com.example.instagram.Service.UserService;
import com.example.instagram.form.UserForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class userController {
    private final UserService userService;

    @GetMapping("/join")
    public String join(UserForm userForm){
        return "join";
    }
    @PostMapping("/join")
    public String join2(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "join";
        }
        if(!userForm.getPassword1().equals(userForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 틀렸습니다.");
            return "join";
        }
        try{
            userService.create(userForm.getUsername(), userForm.getName(),userForm.getPassword1());
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "join";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "join";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/main")
    public String mainPage(Principal principal, Model model) {
        String username = principal.getName();
        Optional<SiteUser> optionalUser = userService.getUserByUsername(username);
        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            model.addAttribute("user", user);
        } else {
            // 처리할 로직 추가 (예: 에러 메시지 표시 등)
        }
        return "main";
    }
    @GetMapping("/profile/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        SiteUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }
    @GetMapping("/profile/edit")
    public String editProfilePage(Principal principal, Model model) {
        String username = principal.getName();
        Optional<SiteUser> optionalUser = userService.getUserByUsername(username);
        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            model.addAttribute("user", user);
            return "edit";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("profilePicture") MultipartFile profilePicture,
                                Principal principal) {
        String currentUsername = principal.getName();
        Optional<SiteUser> optionalUser = userService.getUserByUsername(currentUsername);
        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            try {
                userService.profileEdit(user, name, password, profilePicture);
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리 로직 추가 (예: 로그 기록, 사용자에게 오류 메시지 표시 등)
            }
            return "redirect:/profile/" + user.getId();  // 사용자의 프로필 페이지로 리디렉션
        }
        return "redirect:/main";
    }

}
