package com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long>
{

}
