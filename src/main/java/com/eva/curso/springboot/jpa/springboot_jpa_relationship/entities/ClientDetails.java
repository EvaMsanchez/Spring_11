package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients_details")
public class ClientDetails 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean premiun;
    private Integer points;

    // Clase hija o dependiente, donde va la FK
    @OneToOne
    @JoinColumn(name = "id_cliente") // Dueña de la relación, aquí en detalles va la FK de cliente
    private Client client;


    public ClientDetails() {}

    public ClientDetails(boolean premiun, Integer points) {
        this.premiun = premiun;
        this.points = points;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isPremiun() {
        return premiun;
    }
    public void setPremiun(boolean premiun) {
        this.premiun = premiun;
    }
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", premiun=" + premiun +
                ", points=" + points + "}";
    }
    
}
