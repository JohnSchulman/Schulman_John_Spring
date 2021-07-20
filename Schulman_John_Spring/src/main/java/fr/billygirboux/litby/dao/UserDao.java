package fr.billygirboux.litby.dao;
import fr.billygirboux.litby.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

   /* @Autowired
    private UserService userService;

    userService.



    public UserTo createUser(AddUpdateUserRequestTo userRequestTo);*/

    //Optional<T> get(long id);

    //List<T> getAll();

   // void save(T t);

    //void update(T t, String[] params);

   // void delete(T t);


}
