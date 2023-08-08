package com.ll.DevNews.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @Column(unique = true)
    private String nickname;

    private LocalDate createDate;
}
