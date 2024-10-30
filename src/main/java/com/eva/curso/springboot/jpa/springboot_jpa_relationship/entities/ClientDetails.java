package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Override
    public String toString() {
        return "{id=" + id +
                ", premiun=" + premiun +
                ", points=" + points + "}";
    }
    
}
