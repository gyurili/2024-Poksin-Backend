package com.viewmore.poksin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MainUserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 아이디
    private String username;
    // 비밀번호
    private String password;

    public MainUserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
