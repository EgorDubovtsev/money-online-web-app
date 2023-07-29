package com.transfer.online.repository;

import com.transfer.online.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity,Long> {
    ClientEntity findByClientId(Long clientId);
    ClientEntity findByAccount(String account);
}
