package com.fazeem.personservice.controller;

import com.fazeem.personservice.model.PersonModel;
import com.fazeem.personservice.model.PersonPostModel;
import com.fazeem.personservice.model.PersonResponseContainerModels;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(properties="spring.config.location=classpath:application-test.yml")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTest {
    @Autowired
    PersonController personController;
    static List<PersonModel> personModelList=new ArrayList<>();
    @BeforeAll
     void startUp(){
        ResponseEntity<PersonResponseContainerModels> responseEntity = personController.getAll(10, 0);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), "ResponseStatus Should be 200");
        Assert.notNull(responseEntity.getBody(), "Body MustNot be null");
        Assert.notNull(responseEntity.getBody().getPersonModels(), "Model List MustNot be null");
        Assert.notEmpty(responseEntity.getBody().getPersonModels(), "Model MustNot be empty");
        Assert.isTrue(responseEntity.getBody().getPersonModels().size()>1, "Model MustNot be empty");
        personModelList=responseEntity.getBody().getPersonModels();
    }

    @Test
    void getByIdSuccessTest() {
        ResponseEntity<PersonModel> responseEntity =personController.getById(personModelList.get(0).getId());
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), "ResponseStatus Should be 200");
        Assert.notNull(responseEntity.getBody(), "Body MustNot be null");
        Assert.isTrue(responseEntity.getBody().equals(personModelList.get(0)),"Get Model is not Matching");
    }

    @Test
    void getByIdSuccessFailed() {
        ResponseEntity<PersonModel> responseEntity =personController.getById(Long.MAX_VALUE);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND), "Response Should be "+HttpStatus.NOT_FOUND);
    }

    @Test
    void postPersonTest() {
        PersonPostModel personModel= PersonPostModel.builder()
                .age(35).
                firstName("Test1")
                .lastName("Test1Last").favoriteColour("RED").build();
        ResponseEntity<PersonModel> responseEntity = personController.postPerson(personModel);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED), "ResponseStatus Should be 201");
        Assert.notNull(responseEntity.getBody(), "Body MustNot be null");
        Assert.isTrue(responseEntity.getBody().getAge().equals(personModel.getAge()), "Age not Matching");
        Assert.isTrue(responseEntity.getBody().getFirstName().equals(personModel.getFirstName()), "FirstName not Matching");
        Assert.isTrue(responseEntity.getBody().getLastName().equals(personModel.getLastName()), "LastName not Matching");

    }

    @Test
    void putPersonTestNotFound() {
        PersonPostModel personModel= PersonPostModel.builder()
                                 .age(35)
                                 .firstName("Fazeem")
                                 .lastName("Mohammed").favoriteColour("RED").build();
        ResponseEntity<PersonModel> responseEntity = personController.putPerson(Long.MAX_VALUE,personModel);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND), "Expected not Found");


    }
    @Test
    void putPersonTestSuccess() {
        PersonPostModel personPostModel=new PersonPostModel();
        BeanUtils.copyProperties(personModelList.get(1), personPostModel);
        ResponseEntity<PersonModel> responseEntity = personController.putPerson(personModelList.get(1).getId(),personPostModel);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), "ResponseStatus Should be 201");

    }

    @Test
    void deletePersonSuccess() {

        ResponseEntity<Object> responseEntity = personController.deletePerson(personModelList.get(1).getId());
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT), "ResponseStatus Should be 204");

    }

    @Test
    void deletePersonFailure() {

        ResponseEntity<Object> responseEntity = personController.deletePerson(personModelList.get(1).getId());
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND), "ResponseStatus Should be 404");
        personController.getById(personModelList.get(1).getId());
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND), "Response Should be "+HttpStatus.NOT_FOUND);

    }


}
