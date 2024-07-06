package com.atoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atoms.model.Role;

public interface RoleRespository extends JpaRepository<Role, Long> {

	Role findByRoleName(String string);


}
