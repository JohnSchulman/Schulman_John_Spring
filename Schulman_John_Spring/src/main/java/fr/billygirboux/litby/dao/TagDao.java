package fr.billygirboux.litby.dao;
import fr.billygirboux.litby.model.entity.Role;
import fr.billygirboux.litby.model.entity.RoleId;
import fr.billygirboux.litby.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, String> {


}
