package fr.billygirboux.litby.controller;
import fr.billygirboux.litby.model.entity.User;
import fr.billygirboux.litby.model.to.UserTo;
import fr.billygirboux.litby.model.to.request.AddUpdateUserRequestTo;
import fr.billygirboux.litby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // C'est que dans le create que tu expose ton mot de passe mais dans ton DTO
    @PostMapping
    public UserTo createUser(@Valid @RequestBody AddUpdateUserRequestTo request) {
        return userService.createUser(request);
    }

    @GetMapping()
    public  List<UserTo> getAllUsers() {
         return userService.getall();
    }

    @GetMapping("{id}")
    public UserTo getUser(@PathVariable("id") Long id) {
        return userService.getUserbyId(id);
    }

    @GetMapping("me")
    public UserTo getAuthenticatedUser() {
        return userService.getAuthenticatedUserTo();
    }


    @PatchMapping("{id}")
    public UserTo patchUser(@PathVariable("id") Long id, @Valid @RequestBody AddUpdateUserRequestTo request) {
        request.setId(id);
        return userService.patchUser(request);
    }

    @RequestMapping("user1")
    public User getUser() {
        User u = new User();
        u.setUsername("Billy");
        u.setPassword("Girboux");
        return u;
    }
}
