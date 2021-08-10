package com.rakesh.tutorials.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.tutorials.dto.Tutorial;
import com.rakesh.tutorials.repository.TutorialRepository;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;
	
	@GetMapping("/demo")
	public Object demo() {
		System.out.println("Controller called");
		return "demo";
	}
	//getAllTutorials(): returns List of Tutorials, if there is title parameter, it returns a List in that each Tutorial contains the title
	//Repository’s findAll()
	@GetMapping("/tutorials")
	  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		System.out.println("Controller called");
		try {
		    List<Tutorial> tutorials = new ArrayList<>();

		    if (title == null)
		      tutorialRepository.findAll().forEach(tutorials::add);
		    else
		      tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

		    if (tutorials.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    return new ResponseEntity<>(tutorials, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }

	//getTutorialById(): returns Tutorial by given id
	  @GetMapping("/tutorials/{id}")
	  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
		  System.out.println("Controller called");
		  Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

		  if (tutorialData.isPresent()) {
		    return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	  }

	  
	  //A new Tutorial will be created by MongoRepository.save() method.
	  @PostMapping("/tutorials")
	  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		  System.out.println("Controller called");
	    
		  try {
			    Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
			    return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
			  } catch (Exception e) {
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  //@PutMapping will help us handle PUT HTTP requests.
	  //– updateTutorial() receives id and a Tutorial payload.
	  //– from the id, we get the Tutorial from database using findById() method.
	  //– then we use the payload and save() method for updating the Tutorial.
	  @PutMapping("/tutorials/{id}")
	  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
		  System.out.println("Controller called");
	   
		  Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		  
		  if (tutorialData.isPresent()) {
			    Tutorial _tutorial = tutorialData.get();
			    _tutorial.setTitle(tutorial.getTitle());
			    _tutorial.setDescription(tutorial.getDescription());
			    _tutorial.setPublished(tutorial.isPublished());
			    return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
			  } else {
			    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  }
	  }

	  //deleteTutorial(): delete a Tutorial document with given id
	  //deleteById()
	  @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
		  System.out.println("Controller called");
		  try {
			    tutorialRepository.deleteById(id);
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			  } catch (Exception e) {
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  //deleteAllTutorials(): remove all documents in tutorials collection
	  //deleteAll()
	  @DeleteMapping("/tutorials")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
		  System.out.println("Controller called");
		  try {
			    tutorialRepository.deleteAll();
			    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			  } catch (Exception e) {
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

	  //findByPublished(): return published Tutorials
	  @GetMapping("/tutorials/published")
	  public ResponseEntity<List<Tutorial>> findByPublished() {
		  try {
			    List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

			    if (tutorials.isEmpty()) {
			      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			    }
			    return new ResponseEntity<>(tutorials, HttpStatus.OK);
			  } catch (Exception e) {
			    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			  }
	  }

}
