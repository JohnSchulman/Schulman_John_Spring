package fr.billygirboux.litby.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name="roles")
public class Role implements Serializable {

    private RoleId id;
    private User user;

    @EmbeddedId
    public RoleId getId() {
        return id;
    }

    public void setId(RoleId id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "userId", insertable =false, updatable=false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
