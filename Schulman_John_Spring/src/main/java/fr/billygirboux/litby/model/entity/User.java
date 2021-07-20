package fr.billygirboux.litby.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//@Table(name = "users", catalog = "bitylike")
@Table(name = "users")
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime tCreate;
    private List<Role> roles = new ArrayList<>();

    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "tCreate")
    public LocalDateTime gettCreate() {
        return tCreate;
    }

    public void settCreate(LocalDateTime tCreate) {
        this.tCreate = tCreate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
