
package com.portfolio.Jesica.Controller;

import com.portfolio.Jesica.Dto.dtoPersona;
import com.portfolio.Jesica.Entity.Persona;
import com.portfolio.Jesica.Security.Controller.Mensaje;
import com.portfolio.Jesica.Service.ImpPersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/persona")
@CrossOrigin(origins = "https://frontend-70891.web.app")

public class PersonaController {  
    @Autowired
    public ImpPersonaService personaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list(){
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

   
    @GetMapping("/detail/{id}")
    
    public ResponseEntity<Persona> getById(@PathVariable("id") int id){
        if(!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe el id"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);

        personaService.delete(id);
        return new ResponseEntity(new Mensaje("Persona eliminada"), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody dtoPersona dtoPersona){
        if(StringUtils.isBlank(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre de la persona es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getApellido()))
            return new ResponseEntity(new Mensaje("El apellido de la persona es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getDescripcion()))
            return new ResponseEntity(new Mensaje("La descripcion de la persona es obligatoria"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getDescripcion()))
            return new ResponseEntity(new Mensaje("La imagen de la persona es obligatoria"), HttpStatus.BAD_REQUEST);

        if(personaService.existsByNombre(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("Esa persona ya existe"), HttpStatus.BAD_REQUEST);
        Persona persona = new Persona(dtoPersona.getNombre(), dtoPersona.getApellido(),
                dtoPersona.getDescripcion(), dtoPersona.getImg());
        personaService.save(persona);

        return new ResponseEntity(new Mensaje("Persona agregada"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoPersona dtoPersona){
        // Validacion del ID
        if(!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);

        // Comparar nombres de skill
        if(personaService.existsByNombre(dtoPersona.getNombre()) && personaService.getByNombre(dtoPersona.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("Esa persona ya existe"), HttpStatus.BAD_REQUEST);
        // No puede estar vacio
        if(StringUtils.isBlank(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre de la persona es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getApellido()))
            return new ResponseEntity(new Mensaje("El apellido de la persona es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getDescripcion()))
            return new ResponseEntity(new Mensaje("La descripcion de la persona es obligatoria"), HttpStatus.BAD_REQUEST);

        Persona persona = personaService.getOne(id).get();

        persona.setNombre(dtoPersona.getNombre());
        persona.setApellido(dtoPersona.getApellido());
        persona.setDescripcion(dtoPersona.getDescripcion());
        persona.setImg(dtoPersona.getImg());

        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona actualizada"), HttpStatus.OK);
    }
 
}    
    
