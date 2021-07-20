package fr.billygirboux.litby.dao;

import fr.billygirboux.litby.model.entity.Role;
import fr.billygirboux.litby.model.entity.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, RoleId> {


}
