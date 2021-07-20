package fr.billygirboux.litby.model.to;
import fr.billygirboux.litby.model.entity.Link;
import fr.billygirboux.litby.model.entity.Tag;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkTo implements ToConverter<Link>{

    private String token;
    private String name;
    private String url;
    private LocalDateTime tCreate;
    private List<String> tags = new ArrayList<>();

    public LinkTo() {

    }

    public LinkTo(Link link) {
        this.fromEntity(link);
    }

    // une convertor DTO
    @Override
    public void fromEntity(Link link) {
        this.name = link.getName();
        this.token = link.getToken();
        this.tCreate = link.gettCreate();

        if (!CollectionUtils.isEmpty(link.getTags())) {
            this.tags = link.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime gettCreate() {
        return tCreate;
    }

    public void settCreate(LocalDateTime tCreate) {
        this.tCreate = tCreate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
