package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Persona;

public interface PersonaService {
	
	
    public  List<Persona> getPersonas();

    public Persona getPersona(int idPersona);
    
    public Persona savePerson(Persona id);

    public void deletePerson(int idPersona);

    public void updatePerson(Persona persona);

    public Persona getEmail(String mail);
}
