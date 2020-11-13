package dev.cheerfun.pixivic.biz.web.user.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.cheerfun.pixivic.basic.sensitive.annotation.SensitiveCheck;
import dev.cheerfun.pixivic.biz.web.common.po.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author OysterQAQ
 * @version 1.0
 * @date 2019/08/17 0:09
 * @description UserSignUpDTO
 */
@Data
public class SignUpDTO {
    @NotBlank
    @SensitiveCheck
    @Size(min = 2, max = 40)
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    private String gRecaptchaResponse;

    public User castToUser() {
        return new User(username, email, password);
    }
}
