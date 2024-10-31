package com.eva.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.ClientDetails;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Course;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Student;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.CourseRepository;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner
{
	// Inyectamos las interfaces
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		manyToManyRemoveBidireccionalFindById();
	}


	// MANY TO MANY (BIDIRECCIONAL)
	// ELIMINAR un objeto dependiente o hijo en relación MANY TO MANY
	// Crear estudiantes y cursos, a continuación asignar al estudiante los cursos y guardar estudiantes
	@Transactional
	public void manyToManyBidireccionalRemove()
	{
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andrés");
		Course course2 = new Course("Curso de Spring Boot", "Andrés");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);

		// Eliminar un curso
		Optional<Student> optionalStudentDb = studentRepository.findOneWithCourses(3L); // buscar el estudiante junto con sus cursos, a través de query del repositorio

		if(optionalStudentDb.isPresent())
		{
			Student studentDb = optionalStudentDb.get();
			Optional<Course> optionalCourseDb = courseRepository.findOneWithStudents(3L);

			if(optionalCourseDb.isPresent())
			{
				Course CourseDb = optionalCourseDb.get();

				studentDb.removeCourse(CourseDb);
				studentRepository.save(studentDb);

				System.out.println(studentDb);
			}
		}
	}


	// MANY TO MANY (BIDIRECCIONAL)
	// ELIMINAR un objeto dependiente o hijo en relación MANY TO MANY
	// Igual pero BUSCAR estudiantes y cursos por id

	@Transactional
	public void manyToManyRemoveBidireccionalFindById()
	{
		Optional<Student> optionalStudent1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> optionalStudent2 = studentRepository.findOneWithCourses(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).get();
		Course course2 = courseRepository.findOneWithStudents(2L).get();

		// student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		// student2.setCourses(Set.of(course2));
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);

		// Eliminar un curso
		Optional<Student> optionalStudentDb = studentRepository.findOneWithCourses(1L); // buscar el estudiante junto con sus cursos, a través de query del repositorio

		if(optionalStudentDb.isPresent())
		{
			Student studentDb = optionalStudentDb.get();
			Optional<Course> optionalCourseDb = courseRepository.findOneWithStudents(1L);

			if(optionalCourseDb.isPresent())
			{
				Course CourseDb = optionalCourseDb.get();

				studentDb.removeCourse(CourseDb);
				studentRepository.save(studentDb);

				System.out.println(studentDb);
			}
		}
	}


	// MANY TO MANY (BIDIRECCIONAL)
	// Crear estudiantes y cursos, a continuación asignar al estudiante los cursos y guardar estudiantes
	// No hay que guardar los cursos antes porque con "cascade" realiza el proceso de guardarlos automáticamente al guardar el estudiante
	@Transactional
	public void manyToManyBidireccional()
	{
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andrés");
		Course course2 = new Course("Curso de Spring Boot", "Andrés");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);
	}


	// MANY TO MANY (BIDIRECCIONAL)
	// Igual pero BUSCAR estudiantes y cursos por id
	@Transactional
	public void manyToManyBidireccionalFindById()
	{
		Optional<Student> optionalStudent1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> optionalStudent2 = studentRepository.findOneWithCourses(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).get();
		Course course2 = courseRepository.findOneWithStudents(2L).get();

		// student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		// student2.setCourses(Set.of(course2));
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);
	}


	// MANY TO MANY
	// ELIMINAR un objeto dependiente o hijo
	// Crear estudiantes y cursos, a continuación asignar al estudiante los cursos y guardar estudiantes
	@Transactional
	public void manyToManyRemove()
	{
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andrés");
		Course course2 = new Course("Curso de Spring Boot", "Andrés");

		student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);

		// Eliminar un curso
		Optional<Student> optionalStudentDb = studentRepository.findOneWithCourses(3L); // buscar el estudiante junto con sus cursos, a través de query del repositorio

		if(optionalStudentDb.isPresent())
		{
			Student studentDb = optionalStudentDb.get();
			Optional<Course> optionalCourseDb = courseRepository.findById(3L);

			if(optionalCourseDb.isPresent())
			{
				Course CourseDb = optionalCourseDb.get();

				studentDb.getCourses().remove(CourseDb);
				studentRepository.save(studentDb);

				System.out.println(studentDb);
			}
		}
	}


	// MANY TO MANY
	// ELIMINAR un objeto dependiente o hijo
	// Igual pero BUSCAR estudiantes y cursos por id
	@Transactional
	public void manyToManyRemoveFind()
	{
		Optional<Student> optionalStudent1 = studentRepository.findById(1L);
		Optional<Student> optionalStudent2 = studentRepository.findById(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);

		// Eliminar un curso
		Optional<Student> optionalStudentDb = studentRepository.findOneWithCourses(1L); // buscar el estudiante junto con sus cursos, a través de query del repositorio

		if(optionalStudentDb.isPresent())
		{
			Student studentDb = optionalStudentDb.get();
			Optional<Course> optionalCourseDb = courseRepository.findById(2L);

			if(optionalCourseDb.isPresent())
			{
				Course CourseDb = optionalCourseDb.get();

				studentDb.getCourses().remove(CourseDb);
				studentRepository.save(studentDb);

				System.out.println(studentDb);
			}
		}
	}


	// MANY TO MANY
	// Crear estudiantes y cursos, a continuación asignar al estudiante los cursos y guardar estudiantes
	// No hay que guardar los cursos antes porque con "cascade" realiza el proceso de guardarlos automáticamente al guardar el estudiante
	@Transactional
	public void manyToMany()
	{
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andrés");
		Course course2 = new Course("Curso de Spring Boot", "Andrés");

		student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);
	}


	// MANY TO MANY
	// Igual pero BUSCAR estudiantes y cursos por id
	// No hay que guardar los cursos antes porque con "cascade" realiza el proceso de guardarlos automáticamente al guardar el estudiante
	@Transactional
	public void manyToManyFindById()
	{
		Optional<Student> optionalStudent1 = studentRepository.findById(1L);
		Optional<Student> optionalStudent2 = studentRepository.findById(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2)); // o esto otro: student1.getCourses().add(course1); con el set reemplaza la colección entera
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2)); // Al guardar los estudiantes se guardan los cursos, porque lo realiza en cascada

		System.out.println(student1);
		System.out.println(student2);
	}


	// ONE TO ONE (BIDIRECCIONAL)
	// Crear cliente y crear detalles de cliente, a continuación asignar en el cliente el detalle y en el detalle el cliente, guardar el cliente
	@Transactional
	public void oneToOneBidireccional()
	{
		Client client = new Client("Erba", "Pura");

		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientDetails.setClient(client);
		
		clientRepository.save(client);

		System.out.println(client);
	}


	// ONE TO ONE (BIDIRECCIONAL)
	// Igual pero BUSCAR cliente por id
	@Transactional
	public void oneToOneBidireccionalFindById()
	{
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresent(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);

			client.setClientDetails(clientDetails);
			clientDetails.setClient(client);
			
			clientRepository.save(client);

			System.out.println(client);
		});		
	}


	// ONE TO ONE
	// Crear y guardar detalles de un cliente, crear un cliente, a continuación asignar al cliente el detalle y guardar el cliente
	@Transactional
	public void oneToOne()
	{
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}


	// ONE TO ONE
	// Igual pero BUSCAR cliente por id
	public void oneToOneFindById()
	{
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> optionalClient = clientRepository.findOne(2L); // new Client("Erba", "Pura");
		optionalClient.ifPresent(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
	
			System.out.println(client);
		});
	}


	// ONE TO MANY (BIDIRECCIONAL)
	// ELIMINAR un objeto dependiente o hijo
	// Creando el cliente y las facturas
	@Transactional
	public void removeInvoiceBidireccional()
	{
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		Set<Invoice> invoices = new HashSet<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices);

		// Como es una relación bidireccional a las facturas también hay que añadirles el cliente antes de guardarlas
		invoice1.setClient(client);
		invoice2.setClient(client);

		clientRepository.save(client);

		System.out.println(client);

		// Buscamos el cliente
		Optional<Client> optionalClientDb = clientRepository.findOne(3L);

		optionalClientDb.ifPresent(clientDb -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(1L); // Ahora buscamos la factura
			
			invoiceOptional.ifPresent(invoice -> {
				clientDb.getInvoices().remove(invoice); // Eliminamos la factura
				invoice.setClient(null); // Y eliminamos el cliente de esa factura

				clientRepository.save(clientDb); // Se guarda el cliente
				System.out.println(clientDb);
			});
		});
	}


	// ONE TO MANY (BIDIRECCIONAL)
	// ELIMINAR un objeto dependiente o hijo
	// Igual pero BUSCAR cliente por id
	@Transactional
	public void removeInvoiceBidireccionalFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 8000L);
	
			Set<Invoice> invoices = new HashSet<>();
			invoices.add(invoice1);
			invoices.add(invoice2);
			client.setInvoices(invoices);
	
			// Como es una relación bidireccional a las facturas también hay que añadirles el cliente antes de guardarlas
			invoice1.setClient(client);
			invoice2.setClient(client);
	
			clientRepository.save(client);
	
			System.out.println(client);
		});

		// Buscamos el cliente
		Optional<Client> optionalClientDb = clientRepository.findOne(1L);

		optionalClientDb.ifPresent(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L); // Ahora buscamos la factura
			
			invoiceOptional.ifPresent(invoice -> {
				client.getInvoices().remove(invoice); // Eliminamos la factura
				invoice.setClient(null); // Y eliminamos el cliente de esa factura

				clientRepository.save(client); // Se guarda el cliente
				System.out.println(client);
			});
		});
	}


	// ONE TO MANY (BIDIRECCIONAL)
	// Crear cliente, crear las facturas, al cliente le añadimos las facturas pero a cada factura también hay que añadirle el cliente y luego guardamos el cliente
	@Transactional
	public void oneToManyInvoiceBidireccional()
	{
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		Set<Invoice> invoices = new HashSet<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices);

		// Como es una relación bidireccional a las facturas también hay que añadirles el cliente antes de guardarlas
		invoice1.setClient(client);
		invoice2.setClient(client);

		clientRepository.save(client);

		System.out.println(client);
	}


	// ONE TO MANY (BIDIRECCIONAL)
	// Igual pero BUSCAR cliente por id
	@Transactional
	public void oneToManyInvoiceBidireccionalFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 8000L);
	
			Set<Invoice> invoices = new HashSet<>();
			invoices.add(invoice1);
			invoices.add(invoice2);
			client.setInvoices(invoices);
	
			// Como es una relación bidireccional a las facturas también hay que añadirles el cliente antes de guardarlas
			invoice1.setClient(client);
			invoice2.setClient(client);
	
			clientRepository.save(client);
	
			System.out.println(client);
		});
	}


	// ONE TO MANY
	// ELIMINAR un objeto dependiente o hijo
	// Crear un cliente, crear las direcciones, añadir las direcciones al cliente y guardar el cliente, automáticamente se guardarán también las direcciones. 
	// Lo siguiente buscar el cliente por id y eliminar una dirección específica */ 
	@Transactional
	public void removeAddress()
	{
		Client client = new Client("Fran", "Moras");
		
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			/* En este caso como no es app web, para que se pueda eliminar un objeto mediante un criterio de búsqueda, por id, 
			hay que crear en la clase entity los métodos hasCode() and equals() */ 
			c.getAddresses().remove(address1); 
			clientRepository.save(c);
			System.out.println(c);
		});
	}


	// ONE TO MANY
	// ELIMINAR un objeto dependiente o hijo
	// Igual pero BUSCAR cliente por id
	// Lo siguiente buscar el cliente por id y eliminar una dirección específica
	@Transactional
	public void removeAddressFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client); // actualiza el cliente con las dos direcciones

			System.out.println(client);

			//Optional<Client> optionalClient2 = clientRepository.findById(2L);
			// Para buscar no solo el cliente, sino tambien las direcciones del cliente, para app no web, de terminal o escritorio
			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L); 
			optionalClient2.ifPresent(c -> {
				/* En este caso como no es app web, para que se pueda eliminar un objeto mediante un criterio de búsqueda, por id, 
				hay que crear en la clase entity los métodos hasCode() and equals() */ 
				c.getAddresses().remove(address2); 
				clientRepository.save(c);
				System.out.println(c);
			});
		});
	}


	// ONE TO MANY
	// Crear un cliente, crear las direcciones y agregarlas al cliente, a continuación al guardar el cliente se guardan automáticamente también las direcciones. 
	// No hay que guardar las direcciones antes porque con "cascade" realiza el proceso de guardarlas automáticamente al guardar el cliente
	@Transactional
	public void oneToMany()
	{
		Client client = new Client("Fran", "Moras");
		
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}


	// ONE TO MANY
	// Igual pero BUSCAR cliente por id
	// No hay que guardar las direcciones antes porque con "cascade" realiza el proceso de guardarlas automáticamente al guardar el cliente
	@Transactional
	public void oneToManyFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client); // actualiza el cliente con las dos direcciones

			System.out.println(client);
		});
	}


	// MANY TO ONE
	// Crear y guardar un cliente, crear una factura, a continuación asignar el cliente a esa factura y guardar la factura
	@Transactional
	public void manyToOne()
	{
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}


	// MANY TO ONE
	// Igual pero BUSCAR cliente por id
	@Transactional
	public void manyToOneFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findById(1L);

		if(optionalClient.isPresent())
		{
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}

}
