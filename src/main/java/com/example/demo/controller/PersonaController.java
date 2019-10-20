package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MensajeResp;
import com.example.demo.entity.Persona;
import com.example.demo.entity.Phones;
import com.example.demo.service.PersonaService;

	@RestController
	public class PersonaController {

	    @Autowired
	    private PersonaService personService;
	    
	    @RequestMapping("/")
	    @ResponseBody
	    public String home() {
	        return "Hello World!";
	    }
	    
	    public void setPersonService(PersonaService personService){
	        this.personService = personService;
	    }

	   
	    @RequestMapping(value = "/personas",  method = RequestMethod.GET)
	    public void  getPeronas() {
	       personService.getPersonas();
	       
	    }

	    @RequestMapping(value = "/persona/{idPersona}",   method = RequestMethod.GET)
	    public ResponseEntity<?> getPersona(@PathVariable int idPersona) {
	    	 Persona persona = personService.getPersona(idPersona);
	    	
	         ResponseEntity<Persona> response;
	         if (persona == null) {
	             response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
	         } else {
	             response = new ResponseEntity<>(persona, HttpStatus.OK);
	         }
	         return new ResponseEntity<>(response, HttpStatus.OK);
	    }


	    
	    @RequestMapping(value = "/persona", method = RequestMethod.POST) 
	    public ResponseEntity<MensajeResp> crearPersona(@RequestBody Persona persona){
	    	
	    	 Date fecha = new Date(); 
	    	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	 StringBuilder mensajeSalida = new StringBuilder();
	    	 MensajeResp msgR = new MensajeResp();
	    	 int idPersona =1;
	    	 //System.out.println("valida correo " + validaEmail(persona.getEmail()));
	    	 
	    	 idPersona =(int)UUID.randomUUID().getLeastSignificantBits();
	    	 System.out.println("idPersona   " + idPersona);
	    	 persona.setIdPersona(idPersona);
	    	
			 if(! validaEmail(persona.getEmail())){
	    		 System.out.println("Error en el Email  " +persona.getEmail());
	    		 msgR.setDato(persona.getEmail());
	    		 msgR.setMessage("El Email debe seguir una expresión regular");
	    		 return new ResponseEntity<>(msgR,HttpStatus.BAD_REQUEST);
	    	 }
	    	
	    	 if(!validaClave(persona.getPassword())){
	    		 System.out.println("La Password debe contener Una Mayuscula, letras minúsculas, y dos números");
	    		 msgR.setDato(persona.getPassword());
	    		 msgR.setMessage("La Password debe contener Una Mayuscula, letras minúsculas, y dos números");
	    		 return new ResponseEntity<>(msgR,HttpStatus.BAD_REQUEST);
	    	 }
	    	 
	    	 
	    	 
	    	 Persona personaMail = personService.getEmail(persona.getEmail());
	    	 if(null != personaMail) {
	    		 System.out.println("El mail ya Existe en la base de datos");
	    		 msgR.setDato(persona.getEmail());
	    		 msgR.setMessage("El mail ya Existe en la base de datos");
	    		 return new ResponseEntity<>(msgR,HttpStatus.BAD_REQUEST);
	    	 }
	    	 
	    	 
	    	
	    	 try {
	    		 for (Phones tel: persona.getPhones()) {
		    		 tel.setPersona(persona);
		    	 }
		    	 
		    	 persona.setFechaCreacion(fecha);
		    	 persona.setFechaModificacion(fecha);
		    	 persona.setLastLogin(fecha);
		    	 persona.setToken(idPersona);
		    	 persona.setActivo(true);
		    	 List<MensajeResp> listaSalida = new  ArrayList<MensajeResp>();
		    	  personService.savePerson(persona);
	    		 listaSalida.add(new MensajeResp("Fecha Creacion ", sdf.format(fecha) ,HttpStatus.OK ));
	    		 listaSalida.add(new MensajeResp("Fecha Modificacion ", sdf.format(fecha) ,HttpStatus.OK ));
	    		 listaSalida.add(new MensajeResp("Last Login ", sdf.format(fecha) ,HttpStatus.OK ));
	    		 listaSalida.add(new MensajeResp("Token ", ""+ idPersona ,HttpStatus.OK ));
	    		 listaSalida.add(new MensajeResp("Activo ", "Si" ,HttpStatus.OK ));
	    		 listaSalida.forEach(dto -> mensajeSalida.append(dto.getDato() + " " + dto.getMessage() +  " "));
	    		 msgR.setMessage(mensajeSalida.toString());
	    		 return new ResponseEntity<>(msgR,HttpStatus.OK);
	    	 }catch(Exception e){
	    		 System.out.println("Error al Grabar Persona y sus Telefonos " + e.toString());
	    		 msgR.setDato("mensaje de error");
	    		 msgR.setMessage( e.toString());
	    		 return new ResponseEntity<>(msgR,HttpStatus.BAD_REQUEST);
	    	 }	
	    		
	    }

	   
	    @PutMapping(value = "/persona/{idPersona}") 	   
	    public void updateEmployee(@PathVariable int idPersona, @RequestBody Persona pers){
	   	    System.out.println("updateEmployee  Successfully "  + idPersona);
	   	    Persona persona = personService.getPersona(idPersona);
	   	    pers.setIdPersona(idPersona);
	   	    pers.setFechaModificacion(new Date());
	    	if (persona != null) {
	    		 personService.updatePerson(pers);
	        }
	    	System.out.println(" updateEmployee ");
	    }
	    
	   
	    @RequestMapping(value = "/persona/{idPersona}",  method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteEmployee(@PathVariable int idPersona){
	        personService.deletePerson(idPersona);
	        System.out.println("Employee Deleted Successfully");
	       
	    }
	    
	    
	    public static Boolean validaEmail (String email) {
			Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
	    
	    public static Boolean validaClave(String password) {
	        //1 mayuscula, 1 minuscula, 1 numero minimo
	        boolean resp=false ;
	        char clave;
	        byte  contNumero = 0, contLetraMay = 0, contLetraMin=0;
	        for (byte i = 0; i < password.length(); i++) {
	                 clave = password.charAt(i);
	                String passValue = String.valueOf(clave);
	                 if (passValue.matches("[A-Z]")) {
	                     contLetraMay++;
	                 } else if (passValue.matches("[a-z]")) {
	                     contLetraMin++;
	                 } else if (passValue.matches("[0-9]")) {
	                     contNumero++;
	                 }
	         }
	       
	         if((contLetraMay>0) && (contLetraMin >0) && (contNumero >1) )
	        	 resp=true;
	         
	         return resp;
	  
	     }
}

