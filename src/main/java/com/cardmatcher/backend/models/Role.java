package com.cardmatcher.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "roles")
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;

    // @ManyToMany(mappedBy = "roles")
    // @JsonManagedReference
    // private Set<User> users;

    public enum RoleName {
        USER,
        ADMIN
    }

}
