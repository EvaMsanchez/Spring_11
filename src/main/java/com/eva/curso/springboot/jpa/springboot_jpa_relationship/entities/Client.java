package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients")
public class Client 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    // Relación: la primera parte de la anotación se refiere a la clase donde está anotada, un cliente puede tener muchas direcciones
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "client_id") // personalizar el nombre de la llave foránea(FK), se coloca en la tabla de muchos
    /* SIN el JoinColumn: se crea automáticamente una TABLA INTERMEDIA entre ambas (igual que cuando es una relación muchos a muchos), 
    de lo contrario hay que indicar para que esto no suceda y se cree FK en la tabla correspondiente */

    @JoinTable(
        name = "tbl_clientes_to_direcciones", 
        joinColumns = @JoinColumn(name = "id_cliente"),
        inverseJoinColumns = @JoinColumn(name = "id_direcciones"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    private List<Address> addresses = new ArrayList<>(); // inicializar la lista


    public Client() {}

    public Client(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public List<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", lastname=" + lastname + ", addresses=" + addresses + "}";
    }
    
}
