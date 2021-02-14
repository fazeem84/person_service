package com.fazeem.personservice.controller;

import com.fazeem.personservice.model.PersonModel;
import com.fazeem.personservice.model.PersonPostModel;
import com.fazeem.personservice.model.PersonResponseContainerModels;
import com.fazeem.personservice.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PersonController {
    PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("api/persons")
    public ResponseEntity<PersonResponseContainerModels> getAll(@RequestParam(name = "limit", defaultValue = "100",required = false) int size,
                                                               @RequestParam(name = "page", defaultValue = "0",required = false) int page) {
        PersonResponseContainerModels personResponseContainerModels=personService.getAll(size,page);
        log.info("PersonList {}",personResponseContainerModels);
        return new ResponseEntity<>(personResponseContainerModels, HttpStatus.OK);
    }
    @GetMapping("api/person/{id}")
    public ResponseEntity<PersonModel> getById(@PathVariable("id")Long id) {
         PersonModel personModel=personService.getById(id);
         if(personModel!=null){
             return new ResponseEntity<>(personModel, HttpStatus.OK);
         }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    @PostMapping("api/person")
    @Operation(summary = "Create Person endpoint", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<PersonModel> postPerson(@RequestBody PersonPostModel personPostModel ) {
        PersonModel personModel=personService.postPerson(personPostModel);
        log.info("Response -> {}",personModel);

        return new ResponseEntity<>(personModel,HttpStatus.CREATED);
    }
    @PutMapping("api/person/{id}")
    @Operation(summary = "Update the Existing Person", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<PersonModel> putPerson(@PathVariable("id")Long id, @RequestBody PersonPostModel personPostModel) {
        if(personService.getById(id)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            PersonModel personModel=personService.putPerson(id,personPostModel);
            return new ResponseEntity<>(personModel,HttpStatus.OK);
        }
    }

    @DeleteMapping("api/person/{id}")
    @Operation(summary = "DELETE endpoint", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Object> deletePerson(@PathVariable("id")Long id) {
        if(personService.getById(id)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            personService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
