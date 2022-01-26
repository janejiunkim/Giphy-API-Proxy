package com.janekim.gif.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Result class that models the new cleaned Gif object to be be added to Data list
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"gif_id", "url"})
public class Result {
	private String gif_id;
	private String url;

	public Result() {

	}

	public Result( String gif_id,  String url) {
		this.gif_id = gif_id;
		this.url = url;
	}

	@JsonProperty("gif_id")
	public String getGifId() {
		return gif_id;
	}

	@JsonProperty("gif_id")
	public void setGifId(String gif_id) {
		this.gif_id = gif_id;
	}

	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "Result [gif_id=" + gif_id + ", url=" + url + "]";
	}

	
	
	
	

}
