package fr.billygirboux.litby.service;

import fr.billygirboux.litby.dao.LinkDao;
import fr.billygirboux.litby.dao.TagDao;
import fr.billygirboux.litby.exception.AlreadyTokenExistingException;
import fr.billygirboux.litby.model.entity.Link;
import fr.billygirboux.litby.model.entity.Tag;
import fr.billygirboux.litby.model.entity.User;
import fr.billygirboux.litby.model.to.LinkTo;
import fr.billygirboux.litby.model.to.TinyLinkTo;
import fr.billygirboux.litby.model.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LinkService {

    @Autowired
    private UserService userService;

    @Autowired
    private LinkDao linkDao;

    @Autowired
    private TagDao tagDao;

    //@Value("${host.url}")
    //private String hostUrl;

    @Transactional
    public TinyLinkTo createLink(LinkTo linkToCreate) {

        User authenticatedUser = userService.getAuthenticatedUser();

        Link link = new Link();
        link.setName(linkToCreate.getName());
        link.setUrl(linkToCreate.getUrl());
        if (!StringUtils.isEmpty(linkToCreate.getToken())) {
            if (linkDao.findById(linkToCreate.getToken()).isPresent()) {
                throw new AlreadyTokenExistingException();
            }
            link.setToken(linkToCreate.getToken());
        }
        else {
            link.setToken(generateToken());
        }
        link.settCreate(LocalDateTime.now());
        link.setUser(authenticatedUser);
        linkDao.save(link);

        if (!CollectionUtils.isEmpty(linkToCreate.getTags())) {
            for (String tag: linkToCreate.getTags()) {
                Tag tagEntity = tagDao.findById(tag).orElse(null);
                if (tagEntity == null) {
                    tagEntity = new Tag();
                    tagEntity.setName(tag);
                    tagDao.save(tagEntity);
                }
                link.getTags().add(tagEntity);
                linkDao.save(link);
            }
        }

        TinyLinkTo tinyLink = new TinyLinkTo();
        tinyLink.setTinyLink(link.getToken());

        return tinyLink;

    }


/*
    @Transactional
    public LinkTo createLink(LinkTo linkToCreate) {

       // User authenticatedUser = userService.getAuthenticatedUser();

        Link link = new Link();
        link.setName(linkToCreate.getName());
        link.setUrl(linkToCreate.getUrl());
        link.setToken("XXXXXX");
        link.settCreate(LocalDateTime.now());
       // link.setUser(authenticatedUser);
        linkDao.save(link);

        return new LinkTo(link);
    }
 */

    @Transactional
    public List<LinkTo> getall() {

        // Doit passer par ton user avant de passer par ton userTo
        List<Link> PropertyList = linkDao.findAll();
        List<LinkTo> _linkTos = new ArrayList<>();
        // pour chaque element de property list tu boucle (Tu boucle sur de ton property list de user)
        for (Link link:PropertyList){
            // Tu ajoutes ton user ton UserTo puis à ton list _userTos
            _linkTos.add(new LinkTo(link));
        }
        return _linkTos;
    }

    public LinkTo getLinkByToken(String token) {
        return new LinkTo(linkDao.getOne(token));
    }

/*
    public List<LinkTo> getUserLinks(String search) {

        User authenticatedUser = userService.getAuthenticatedUser();
        if (!CollectionUtils.isEmpty(authenticatedUser.getLinks())) {


            List<Link> filteredLinks = null;
            if (StringUtils.isEmpty(search)) {
                filteredLinks = authenticatedUser.getLinks();
            } else {
                filteredLinks = linkDao.searchLinks("%" + search + "%", authenticatedUser.getId());
            }

            return filteredLinks.stream().map(LinkTo::new).collect(Collectors.toList());
        }
        return null;
    }
 */

    @Transactional
    public void delete(String token){

        linkDao.deleteById(token);
    }


    // appel la fonction getRandomToken avec une taille spécique
    // En verifiant d'abord s'il existe
    private String generateToken() {
        String token;

        while (true) {
            token = getRandomToken(6);
            if (!linkDao.findById(token).isPresent()) {
                break;
            }
        }
        return token;
    }



    // génére une token d'une taille spécifique
    private String getRandomToken(int size) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        String token = "";

        for (int i=0; i<size; i++) {
            token += chars.charAt(random.nextInt(chars.length()));
        }
        return token;
    }
}
