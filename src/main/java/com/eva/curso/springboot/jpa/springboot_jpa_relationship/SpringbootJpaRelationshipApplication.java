package com.eva.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.eva.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner
{
	// Inyectamos las interfaces
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		removeAddressFindByIdClient();
	}


	/* Eliminar un objeto dependiente o hijo
	Crear un cliente, crear las direcciones, añadir las direcciones al cliente y guardar el cliente, automáticamente se guardarán
	también las direcciones. Lo siguiente buscar el cliente por id y eliminar una dirección específica */ 
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


	/* Eliminar un objeto dependiente o hijo
	Buscar un cliente por el id, crear las direcciones, añadir las direcciones al cliente y guardar el cliente, automáticamente se guardarán
	también las direcciones. Lo siguiente buscar el cliente por id y eliminar una dirección específica */
	@Transactional
	public void removeAddressFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client); // actualiza el cliente con las dos direcciones

			System.out.println(client);

			//Optional<Client> optionalClient2 = clientRepository.findById(2L);
			// Para buscar no solo el cliente, sino tambien las direcciones del cliente, para app no web, de terminal o escritorio
			Optional<Client> optionalClient2 = clientRepository.findOne(2L); 
			optionalClient2.ifPresent(c -> {
				/* En este caso como no es app web, para que se pueda eliminar un objeto mediante un criterio de búsqueda, por id, 
				hay que crear en la clase entity los métodos hasCode() and equals() */ 
				c.getAddresses().remove(address2); 
				clientRepository.save(c);
				System.out.println(c);
			});
		});
	}


	/* Crear un cliente, crear las direcciones y agregarlas al cliente, a continuación al guardar el cliente se guardan automáticamente 
	también las direcciones. 
	No hay que guardar las direcciones antes porque con "cascade" realiza el proceso de guardarlas automáticamente al guardar el cliente */ 
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


	/* Buscar un cliente por el id, crear las direcciones y agregarlas al cliente, a continuación al guardar el cliente se guardan automáticamente 
	también las direcciones.
	No hay que guardar las direcciones antes porque con "cascade" realiza el proceso de guardarlas automáticamente al guardar el cliente */ 
	@Transactional
	public void oneToManyFindByIdClient()
	{
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client); // actualiza el cliente con las dos direcciones

			System.out.println(client);
		});
	}


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


	// Buscar un cliente por el id, crear la factura, a continuación asignar el cliente buscado a esa factura y guardar
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
