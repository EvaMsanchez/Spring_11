package com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Client, Long>
{
    // Consulta personalizada para buscar un cliente por id junto con sus direcciones, todo en una consulta
    @Query("select c from Client c join fetch c.addresses")
    Optional<Client> findOne(Long id);
}
