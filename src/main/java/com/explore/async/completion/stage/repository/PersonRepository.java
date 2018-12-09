package com.explore.async.completion.stage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.explore.async.completion.stage.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
}
