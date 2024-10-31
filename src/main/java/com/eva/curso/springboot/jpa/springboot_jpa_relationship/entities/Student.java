package com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "students")
public class Student 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    // Relación UNIDIRECCIONAL: indicar cual es la clase principal (a partir de una creamos los hijos dependiente)
    // Clase padre (en este caso se crea tabla intermedia sin personalizar)
    // En este caso NO se puede ELIMINAR los hijos porque los cursos estarán asignados a otros estudiantes, por la relación ManyToMany
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // NO se puede CascadeType.ALL
    // Solo cuando es Many to Many o One to Many(pero aquí sería mejor sin tabla intermedia)
    @JoinTable(name = "tbl_alumnos_cursos",
               joinColumns = @JoinColumn(name = "alumno_id"),
               inverseJoinColumns = @JoinColumn(name = "curso_id"),
               uniqueConstraints =  @UniqueConstraint(columnNames = {"alumno_id", "curso_id"}))
    private Set<Course> courses = new HashSet<>();

    
    public Student() {}

    public Student(String name, String lastname) {
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
    public Set<Course> getCourses() {
        return courses;
    }
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "{id=" + id + 
                ", name=" + name + 
                ", lastname=" + lastname + 
                ", courses=" + courses + 
                "}";
    }

}
