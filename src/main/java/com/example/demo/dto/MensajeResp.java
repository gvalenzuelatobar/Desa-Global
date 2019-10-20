package com.example.demo.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MensajeResp {
	
	public MensajeResp() {

	}

	@JsonProperty("dato")
	private String dato;
	
	@JsonProperty("message")
    private String message;
   
    
	public MensajeResp(String dato, String message, HttpStatus ok) {
		super();
		this.dato = dato;
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

}
