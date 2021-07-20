package fr.billygirboux.litby.dao;

import fr.billygirboux.litby.model.entity.Link;
import fr.billygirboux.litby.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinkDao extends JpaRepository<Link, String> {

    @Query(value = "SELECT l.* FROM links AS l " +
            "LEFT JOIN tagLinks AS tl ON tl.linkId = l.token " +
            "WHERE l.userId = :userId AND (l.name like :search OR tl.tagName like :search)", nativeQuery = true)
    List<Link> searchLinks(String search, Long userId);
}
