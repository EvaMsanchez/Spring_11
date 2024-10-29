package com.eva.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Set;
import java.util.HashSet;
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
		removeInvoiceBidireccional();
	}


	/* Relación bidireccional 
	Eliminar un objeto dependiente o hijo en relación OneToMany 
	Creando el cliente */
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


	/* Relación bidireccional 
	Eliminar un objeto dependiente o hijo en relación OneToMany 
	Pero en vez de crear un cliente, realizar una búsqueda por el id */
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


	/* Relación bidireccional 
	Crear cliente, crear las facturas, al cliente le añadimos las facturas pero a cada factura también hay que añadirle cada cliente y 
	luego guardamos el cliente */
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


	/* Relación bidireccional 
	Pero en vez de crear un cliente, realizar una búsqueda por el id */
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


	/* Eliminar un objeto dependiente o hijo en relación OneToMany
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


	/* Eliminar un objeto dependiente o hijo en relación OneToMany
	Buscar un cliente por el id, crear las direcciones, añadir las direcciones al cliente y guardar el cliente, automáticamente se guardarán
	también las direcciones. Lo siguiente buscar el cliente por id y eliminar una dirección específica */
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

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

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
