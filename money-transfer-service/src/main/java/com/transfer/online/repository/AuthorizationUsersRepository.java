package com.transfer.online.repository;

import com.transfer.online.entity.ServiceUser;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizationUsersRepository extends CrudRepository<ServiceUser, Long> {
    ServiceUser findByUsername(String username);
}
