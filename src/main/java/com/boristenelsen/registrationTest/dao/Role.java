package com.boristenelsen.registrationTest.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
