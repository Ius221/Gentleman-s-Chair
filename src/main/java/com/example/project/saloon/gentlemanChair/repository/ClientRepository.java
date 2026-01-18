package com.example.project.saloon.gentlemanChair.repository;


import com.example.project.saloon.gentlemanChair.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
