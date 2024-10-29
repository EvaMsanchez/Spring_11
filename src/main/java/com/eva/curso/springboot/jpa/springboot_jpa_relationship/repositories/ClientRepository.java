package com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Client, Long>
{
    // Consulta personalizada para buscar un cliente por id junto con sus direcciones, todo en una consulta
    @Query("select c from Client c left join fetch c.addresses where c.id= ?1")
    Optional<Client> findOneWithAddresses(Long id);

    // Consulta personalizada para buscar un cliente por id junto con sus facturas, todo en una consulta
    @Query("select c from Client c left join fetch c.invoices where c.id= ?1")
    Optional<Client> findOneWithInvoices(Long id);

    /* Consulta personalizada para buscar un cliente por id junto con sus facturas y direcciones, para que al obtener un optional
    ya el cliente tenga las facturas y direcciones y se puedan añadir las factura sino hace proxy, al no llevar las facturas el cliente */ 
    @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses where c.id= ?1")
    Optional<Client> findOne(Long id);
}
