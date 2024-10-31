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

    // Relación: indicar cual es la clase principal (a partir de una creamos los hijos dependiente)
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

    // Añadir curso
    public void addCourse(Course course)
    {
        this.courses.add(course); // Añade el curso a la lista de cursos del estudiante
        course.getStudents().add(this); // Añade el estudiante a la lista de estudiantes del curso
    }

    // Eliminar curso
    public void removeCourse(Course course)
    {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "{id=" + id + 
                ", name=" + name + 
                ", lastname=" + lastname + 
                ", courses=" + courses + 
                "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        return true;
    }

}
