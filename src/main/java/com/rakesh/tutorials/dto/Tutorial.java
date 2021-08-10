package com.rakesh.tutorials.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Document annotation helps us override the collection name by “tutorials”.
@Document(collection = "tutorials")
@Data
@NoArgsConstructor
public class Tutorial {
	
	//Our Data model is Tutorial with four fields: id, title, description, published.

	  @Id
	  private String id;
	  private String title;
	  private String description;
	  private boolean published;
	  
	 public Tutorial(String title, String description, boolean published) {
		
		this.title = title;
		this.description = description;
		this.published = published;
	}
	  
	  
}
