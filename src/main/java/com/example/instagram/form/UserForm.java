package com.example.instagram.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    @Size(min=3,max=25)
    @NotEmpty(message = "이름은 필수항목입니다")
    private String name;

    @Size(min=3,max=25)
    @NotEmpty(message = "전화번호는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;


}
