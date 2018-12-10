package com.explore.async.completion.stage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.explore.async.completion.stage.iservice.IAsyncService;
import com.explore.async.completion.stage.model.Person;
import com.explore.async.completion.stage.repository.PersonRepository;
@Service
public class AsyncService implements IAsyncService{
	private final Logger logger = LoggerFactory.getLogger(AsyncService.class);
	@Autowired
	private PersonRepository personRepository;
	@SuppressWarnings("deprecation")
	@Override
	public List<Person> getPersons(int pageNumber, String sortBy) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Callable<List<Person>> personsCallable = ()->personRepository.findAll(new PageRequest(pageNumber, 10, new Sort(Sort.Direction.ASC, sortBy))).getContent();
		Future<List<Person>> personFuture= executorService.submit(personsCallable);
		executorService.shutdown();
		try {
			return personFuture.get();
		}
		catch(InterruptedException | ExecutionException exe) {
			logger.error("Exception occured while executor service processing the request ");
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public List<Person> getPersonsUsingSingleThread(int pageCount) {
		long start = System.currentTimeMillis();
		List<Person> persons = new ArrayList<>();
		for(int index = 0;index<pageCount;index++) {
			persons.addAll(personRepository.findAll(new PageRequest(index, 10)).getContent());
		}
		logger.info("Time taking by in single threaded Env In seconds  "+((System.currentTimeMillis()-start)/1000));
		return persons;
	}
	@SuppressWarnings("deprecation")
	@Override
	public List<Person> getPersonsUsingSyncMultiThreading(int pageCount) {
		List<Person> persons = new ArrayList<>();
		ExecutorService executorsService = Executors.newFixedThreadPool(pageCount);
		long start = System.currentTimeMillis();
		List<Future<List<Person>>> futures = new ArrayList<>();
		Callable<List<Person>> personsCallable = null;
		for(int index=0;index<pageCount;index++) {
			final int counter = index;
			personsCallable= ()->personRepository.findAll(new PageRequest(counter, 10)).getContent();
			futures.add(executorsService.submit(personsCallable));
		}
		executorsService.shutdown();
		futures.stream().map(future->{
			try {
				return future.get();
			}
			catch(InterruptedException|ExecutionException e) {
				logger.error("Error occured "+e.getMessage());
			}
			return null;
		}).collect(Collectors.toList()).forEach(personList->persons.addAll(personList));
		logger.info("Time taken to fetch in  multi threaded environment in seconds "+((System.currentTimeMillis()-start)/1000));
		return persons;
	}
	@Override
	public List<Person> getPersonsUsingAsyncMultiThreading(int pageCount) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
