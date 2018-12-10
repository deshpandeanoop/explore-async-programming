package com.explore.async.completion.stage.iservice;

import java.util.List;

import com.explore.async.completion.stage.model.Person;

public interface IAsyncService {
	public List<Person> getPersons(int pageNumber, String sortBy);
	public List<Person> getPersonsUsingSingleThread(int pageCount);
	public List<Person> getPersonsUsingSyncMultiThreading(int pageCount);
	public List<Person> getPersonsUsingAsyncMultiThreading(int pageCount);
}
