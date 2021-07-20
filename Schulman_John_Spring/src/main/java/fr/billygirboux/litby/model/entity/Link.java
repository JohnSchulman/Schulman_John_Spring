package fr.billygirboux.litby.model.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "links")
public class Link {

    private String token;
    private String name;
    private String url;
    private LocalDateTime tCreate;
    private List<Tag> tags;
    private User user;

    @Id
    @Column(name="token", length = 6)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "tCreate")
    public LocalDateTime gettCreate() {
        return tCreate;
    }

    public void settCreate(LocalDateTime tCreate) {
        this.tCreate = tCreate;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tagLinks",
            joinColumns =  {
                    @JoinColumn(name = "linkId", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tagName", nullable = false, updatable = false)
            })
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
