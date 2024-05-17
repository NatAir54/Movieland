package com.nataliia.koval.movieland.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_id_seq", allocationSize = 20)
    private Integer id;

    private String nickname;

    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }
}
