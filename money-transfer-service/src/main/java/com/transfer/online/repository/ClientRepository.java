package com.transfer.online.repository;

import com.transfer.online.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    ClientEntity findByClientId(Long clientId);
    ClientEntity findByAccount(String account);
}
