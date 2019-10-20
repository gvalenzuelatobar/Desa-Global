package com.example.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Persona;
import com.example.demo.repository.PersonaRepository;
import com.example.demo.service.PersonaService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public void setPersonaRepository(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    public List<Persona> getPersonas(){
        List<Persona> persons = personaRepository.findAll();
        return persons;
    }

    @Override
    public Persona getPersona(int idPersona) {
        Optional<Persona> optPer = personaRepository.findById(idPersona);
        return optPer.get();
    }

    @Override
    public Persona savePerson(Persona persona) {
    	return personaRepository.save(persona);
    }

    @Override
    public void deletePerson(int idPersona) {
    	personaRepository.deleteById(idPersona);
    }

    @Override
    public void updatePerson(Persona persona) {
    	personaRepository.save(persona);
    }
    
    @Override
    public Persona getEmail(String mail) {
        Persona persona = personaRepository.findByEmail(mail);
        return persona;
    }

}
