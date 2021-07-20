package fr.billygirboux.litby.model.to;

import fr.billygirboux.litby.model.entity.Role;
import fr.billygirboux.litby.model.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserTo implements ToConverter<User>{

    private Long id;
    //@Email(message = "Username mandatory")
    private String username;
    private LocalDateTime tCreate;
    private List<String> roles = new ArrayList<>();

    public UserTo() {

    }

    public UserTo(User user) {
        this.fromEntity(user);
    }

    // une convertor DTO
    @Override
    public void fromEntity(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.tCreate = user.gettCreate();

        for (Role role: user.getRoles()) {
            this.roles.add(role.getId().getRoleName());
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime gettCreate() {
        return tCreate;
    }

    public void settCreate(LocalDateTime tCreate) {
        this.tCreate = tCreate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
