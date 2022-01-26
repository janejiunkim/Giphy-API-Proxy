package com.janekim.gif.controllers;

import com.janekim.gif.model.Data;
import com.janekim.gif.service.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Standard Controller following any MVC format
@RestController
public class ResultController {
	
	@Autowired
	private ResultService resultService;

	public ResultController(ResultService resultService) {
		this.resultService = resultService;
	}
	
	// GET route that has path variable to search for
	@GetMapping("/search/{search_term}")
	public Data searchForGifsByTerm(@PathVariable("search_term") String search_term) {
		return resultService.searchForGifs(search_term);
		
	}
	
	

}
