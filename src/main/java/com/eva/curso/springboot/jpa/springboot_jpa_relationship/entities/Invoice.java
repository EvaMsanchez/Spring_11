package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    // Relación: la primera parte de la anotación se refiere a la clase donde está anotada, muchas facturas pertenecen a un cliente
    // Crea el foreign key de forma automática, coge el nombre del atributo y añade id: client_id
    @ManyToOne
    @JoinColumn(name = "client_id") // personalizar el nombre de la llave foránea(FK)
    private Client client;

    public Invoice() {}

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    
    @Override
    public String toString() {
        return "{id=" + id + ", description=" + description + ", total=" + total + ", client=" + client + "}";
    }
    
}
