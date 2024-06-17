package com.example.instagram.Controller;

import com.example.instagram.Service.UserService;
import com.example.instagram.form.UserForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
