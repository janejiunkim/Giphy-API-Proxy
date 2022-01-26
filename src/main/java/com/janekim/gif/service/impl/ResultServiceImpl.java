package com.janekim.gif.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.janekim.gif.model.Data;
import com.janekim.gif.model.Result;
import com.janekim.gif.service.ResultService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Implementation of Service Layer
@Service
public class ResultServiceImpl implements ResultService{
	
	// api key in application.properties
	@Value("${api_key}")
	private String api_key;

	// The number of gif objects for response
	private static final int limit = 5;

	// Giphy API search endpoint
	private static final String base_url = "http://api.giphy.com/v1/gifs/search?";
	

	@Override
	// Method that sends request to Giphy API search endpoint with search term from client
	// Returns cleaned response, rewrapped JSON
	public Data searchForGifs(String search_term) {

		List<Result> results = new ArrayList<Result>();

		RestTemplate restTemplate = new RestTemplate();
		// Build URL to Giphy API with search term from client
		String search_url = base_url + "q=" + search_term + "&api_key=" + api_key + "&limit=" + limit;

		// GET request to Giphy Search
		ResponseEntity<String> responseEntity = 
			restTemplate.getForEntity(search_url, String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		// Turn response body to string
		String rawJson = responseEntity.getBody().toString();
	
		try {
		
			JsonNode jsonNode = objectMapper.readValue(rawJson, JsonNode.class);
			// gets outer "data" object with array of gifs
			JsonNode gifArray = jsonNode.get("data");
			// Number of gif results
			int gifCount = gifArray.size();
			System.out.println("THIS IS HOW MANY GIF OBJECTS IN DATA ARRAY: " + gifCount);

			// If there are 5 (or more but limit has been set to 5 in search endpoint already and we only need 5) results to Gif search
			if (gifCount == 5) {
				// Iterate through array of Gifs
				for(int i=0; i < gifCount; i++) {
					// For each gif in the array
					JsonNode gifNode = gifArray.get(i);
					// Get its id
					JsonNode gifId = gifNode.get("id");
					String gif_id = gifId.asText();
					// Get its url
					JsonNode gifUrl = gifNode.get("url");
					String url = gifUrl.asText();
					// Create a gif result object
					Result newResult = new Result(gif_id, url);
					// Add it to the list of Gif objects
					results.add(newResult);
				}
			} else{
				// There are less than 5 results, let user know
				System.out.println("LESS THAN FIVE RESULTS FOR THIS QUERY :(");
				
			}
			

		} catch (final Exception e) {
			e.printStackTrace();
			
		}
		// Wrap the list of Gif Result Objects into outter data object
		Data data = new Data(results);
		// Return to user
		return data; 
		
	}



}
