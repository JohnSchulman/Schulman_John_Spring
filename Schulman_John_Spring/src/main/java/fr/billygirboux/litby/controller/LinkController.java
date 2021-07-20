package fr.billygirboux.litby.controller;
import fr.billygirboux.litby.model.entity.User;
import fr.billygirboux.litby.model.to.LinkTo;
import fr.billygirboux.litby.model.to.TinyLinkTo;
import fr.billygirboux.litby.model.to.UserTo;
import fr.billygirboux.litby.model.to.request.AddUpdateUserRequestTo;
import fr.billygirboux.litby.service.LinkService;
import fr.billygirboux.litby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
;


@RestController
@RequestMapping("/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping
    public TinyLinkTo createLink(@Valid @RequestBody LinkTo linkToCreate) {
        return linkService.createLink(linkToCreate);
    }

    @GetMapping()
    public  List<LinkTo> getAllLinks() {
        return linkService.getall();
    }

    @DeleteMapping("{token}")
    public void deletLink(@PathVariable("token")  String token){
        linkService.delete(token);
    }

}
