package com.example.demo.login;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginDto {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Login toEntity(){
        return Login.builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
    }

    @Builder
    public LoginDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}