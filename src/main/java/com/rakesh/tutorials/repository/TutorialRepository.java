package com.rakesh.tutorials.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rakesh.tutorials.dto.Tutorial;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {

	  List<Tutorial> findByTitleContaining(String title);
	  List<Tutorial> findByPublished(boolean published);
}

/*
Now we can use MongoRepository’s methods: 
save(), findOne(), findById(), findAll(), count(), delete(), deleteById()… without implementing these methods.

We also define custom finder methods:
– findByTitleContaining(): returns all Tutorials which title contains input title.
– findByPublished(): returns all Tutorials with published having value as input published.

The implementation is plugged in by Spring Data MongoDB automatically.

*/