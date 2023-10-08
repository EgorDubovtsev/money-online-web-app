package com.transfer.online.repository;

import com.transfer.online.entity.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizationUsersRepository extends JpaRepository<ServiceUser, Long> {
    ServiceUser findByUsername(String username);
}
