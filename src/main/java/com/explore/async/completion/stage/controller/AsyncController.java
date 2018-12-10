package com.explore.async.completion.stage.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.explore.async.completion.stage.iservice.IAsyncService;
import com.explore.async.completion.stage.model.Person;
import com.explore.async.completion.stage.repository.PersonRepository;

@RestController
public class AsyncController {
	private final Logger logger = LoggerFactory.getLogger(AsyncController.class);
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private IAsyncService asyncService;
	@PostMapping("/person")
	public List<Person> savePersons(@RequestBody List<Person> persons){
		logger.info("Request process is initiated");
		return personRepository.saveAll(persons);
	}
	@GetMapping("/person")
	public List<Person> getPersons(@QueryParam("pageNumber")int pageNumber,@QueryParam("sortBy")String sortBy){
		return asyncService.getPersons(pageNumber, sortBy);
	}
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id")long id) {
		logger.info("Request processes initiated "+id);
		return personRepository.findById(id).get();
	}
	@GetMapping("/personSingleThread")
	public List<Person> getPersonsSingleThread(@QueryParam("pageCount") int pageCount){
		return asyncService.getPersonsUsingSingleThread(pageCount);
	}
	@GetMapping("/personSyncMultiThread")
	public List<Person> getPersonsSyncMultiThread(@QueryParam("pageCount") int pageCount){
		return asyncService.getPersonsUsingSyncMultiThreading(pageCount);
	}
}
