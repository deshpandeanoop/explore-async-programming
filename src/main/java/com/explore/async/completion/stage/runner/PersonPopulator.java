//package com.explore.async.completion.stage.runner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.explore.async.completion.stage.model.Address;
//import com.explore.async.completion.stage.model.Contact;
//import com.explore.async.completion.stage.model.Person;
//import com.explore.async.completion.stage.repository.PersonRepository;
//@Component
//public class PersonPopulator  implements CommandLineRunner{
//	private final Logger logger = LoggerFactory.getLogger(PersonPopulator.class);
//	@Autowired
//	private PersonRepository personRepository;
//	@Override
//	public void run(String... args) throws Exception {
//		logger.info("Command Line Runner task Initiated");
//		personRepository.saveAll(createPersons());
//		logger.info("Persons persisted successfully");
//	}
//	private List<Person> createPersons(){
//		List<Person> persons = new ArrayList<>();
//		for(int i=1;i<=100000;i++) {
//			Person person = new Person();
//			person.setFirstName("First Name "+i);
//			person.setLastName("LastName "+i);
//			person.setOccupation("Occupation "+i);
//			Address address = new Address();
//			address.setCountry("Country "+i);
//			address.setState("State "+i);
//			List<Contact> contacts = new ArrayList<>();
//			for(int j=1;j<=5;j++) {
//				Contact contact = new Contact();
//				contact.setInfo("123456"+j+i);
//				contact.setType("Type "+j+i);
//				contacts.add(contact);
//			}
//			person.setAddress(address);
//			person.setContacts(contacts);
//			persons.add(person);
//		}	
//		logger.info("Person List formed successfully");
//		return persons;
//	}
//}
