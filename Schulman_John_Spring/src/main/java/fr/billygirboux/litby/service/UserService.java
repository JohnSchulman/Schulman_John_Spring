package fr.billygirboux.litby.service;
import fr.billygirboux.litby.dao.LinkDao;
import fr.billygirboux.litby.dao.RoleDao;
import fr.billygirboux.litby.dao.UserDao;
import fr.billygirboux.litby.model.entity.Role;
import fr.billygirboux.litby.model.entity.RoleId;
import fr.billygirboux.litby.model.entity.User;
import fr.billygirboux.litby.model.to.LinkTo;
import fr.billygirboux.litby.model.to.UserTo;
import fr.billygirboux.litby.model.to.request.AddUpdateUserRequestTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService<userDao> {


    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserTo getAuthenticatedUserTo() {
        return new UserTo(getAuthenticatedUser());
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByUsername(auth.getName());
    }


    @Transactional
    public UserTo createUser(AddUpdateUserRequestTo userRequestTo) {
        User user = new User();
        user.settCreate(LocalDateTime.now());
        user.setUsername(userRequestTo.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRequestTo.getPassword()));
        //user.setPassword(userRequestTo.getPassword());
        userDao.save(user);

        Role role = new Role();
        RoleId roleId = new RoleId();
        roleId.setUserId(user.getId());
        roleId.setRoleName("ROLE_ADMIN");
        role.setId(roleId);

        roleDao.save(role);

        user.getRoles().add(role);

        return new UserTo(user);
    }

    @Transactional
    public List<UserTo> getall() {

        // Doit passer par ton user avant de passer par ton userTo
        List<User> PropertyList = userDao.findAll();
        List<UserTo> _userTos = new ArrayList<>();
        // pour chaque element de property list tu boucle (Tu boucle sur de ton property list de user)
        for (User user:PropertyList){
            // Tu ajoutes ton user ton UserTo puis à ton list _userTos
            _userTos.add(new UserTo(user));
        }
        return _userTos;
    }

   @Transactional
    public UserTo getUserbyId(Long id) {
        checkUserAccess(id);
        UserTo user = new UserTo(userDao.getOne(id));

        return user;
    }


    @Transactional
    public UserTo patchUser(AddUpdateUserRequestTo user) {
        checkUserAccess(user.getId());
        User userToPatch = userDao.getOne(user.getId());
        userToPatch.setUsername(user.getUsername());
        userToPatch.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(userToPatch);
        return new UserTo(userToPatch);
    }



    private void checkUserAccess(Long id) {
        // recuperer la personne authentifié
        UserTo authenticatedUser = getAuthenticatedUserTo();
        // sois tu es admin sois tu est propriétaire de l'id
        if (! authenticatedUser.getRoles().contains("ROLE_ADMIN")
                &&
                ! authenticatedUser.getId().equals(id)) {
            throw new AccessDeniedException("NOT_AUTHORIZED");
        }
    }
}
