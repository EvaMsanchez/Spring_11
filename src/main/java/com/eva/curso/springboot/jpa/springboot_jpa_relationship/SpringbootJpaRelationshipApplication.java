package com.eva.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		manyToOneFindByIdClient();
	}

	// Crear y guardar un cliente, crear una factura, a continuación asignar el cliente a esa factura y guardar la factura
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
