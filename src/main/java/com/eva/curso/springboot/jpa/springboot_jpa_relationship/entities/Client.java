package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    // Relación con direcciones: la primera parte de la anotación se refiere a la clase donde está anotada, un cliente puede tener muchas direcciones
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "client_id") // personalizar el nombre de la llave foránea(FK), se coloca en la tabla de muchos
    /* SIN el JoinColumn: se crea automáticamente una TABLA INTERMEDIA entre ambas (igual que cuando es una relación muchos a muchos), 
    de lo contrario hay que indicar para que esto no suceda y se cree FK en la tabla correspondiente */
    @JoinTable(
        name = "tbl_clientes_to_direcciones", 
        joinColumns = @JoinColumn(name = "id_cliente"),
        inverseJoinColumns = @JoinColumn(name = "id_direcciones"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    private Set<Address> addresses = new HashSet<>(); // inicializar la lista

    // Relación con facturas: un cliente puede tener muchas facturas
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client") // mappedBy= se indica el atributo de la otra clase con la que se relaciona (donde está el JoinColumns)
    private Set<Invoice> invoices = new HashSet<>();

    // Clase padre
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;


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
    public Set<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    public Set<Invoice> getInvoices() {
        return invoices;
    }
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
    public ClientDetails getClientDetails() {
        return clientDetails;
    }
    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    /* Método opcional
    public void addInvoice(Invoice invoice)
    {
        invoices.add(invoice);
        invoice.setClient(this);
    } 
    */

    @Override
    public String toString() {
        return "{id=" + id + 
                ", name=" + name + 
                ", lastname=" + lastname + 
                ", invoices=" + invoices +
                ", addresses=" + addresses + 
                ", clientDetails=" + clientDetails + 
                "}";
    }
    
}
