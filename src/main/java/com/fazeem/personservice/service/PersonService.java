package com.fazeem.personservice.service;

import com.fazeem.personservice.model.PersonPostModel;
import com.fazeem.personservice.repository.PersonRepository;
import com.fazeem.personservice.entity.PersonEntity;
import com.fazeem.personservice.model.Metadata;
import com.fazeem.personservice.model.PersonModel;
import com.fazeem.personservice.model.PersonResponseContainerModels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {

    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonResponseContainerModels getAll(int size, int page) {
        PersonResponseContainerModels personResponseContainerModels = new PersonResponseContainerModels();
        Pageable pagable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<PersonEntity> personEntityPage = personRepository.findAll(pagable);
        List<PersonEntity> personEntityList= personEntityPage.getContent();
        log.info("personEntityList {}",personEntityList);
        List<PersonModel> personModels = personEntityList
                .stream()
                .map(PersonService::generatePersonModel)
                .collect(Collectors.toList());

        personResponseContainerModels.setPersonModels(personModels);
        personResponseContainerModels.setMetadata(generateMetadata(personEntityPage));
        return personResponseContainerModels;

    }

    public PersonModel getById(Long id) {
        PersonModel personModel = null;
        Optional<PersonEntity> personEntity = personRepository.findById(id);
        if (personEntity.isPresent()) {
            personModel = generatePersonModel(personEntity.get());
        }
        return personModel;

    }

    public PersonModel putPerson(Long id,PersonPostModel personPostModel) {
        PersonEntity personEntity = generatePersonEntity(id,personPostModel);
        return savePersonEntity(personEntity);
    }




    public PersonModel postPerson(PersonPostModel personPostModel) {
        PersonEntity personEntity = generatePersonEntity(personPostModel);
        return savePersonEntity(personEntity);
    }
    private PersonModel savePersonEntity(PersonEntity personEntity) {
        personEntity = personRepository.saveAndFlush(personEntity);
        return generatePersonModel(personEntity);
    }



    public void delete(Long id) {
         personRepository.deleteById(id);

    }
    private PersonEntity generatePersonEntity(Long id, PersonPostModel personPostModel) {
        PersonEntity personEntity=generatePersonEntity(personPostModel);
        personEntity.setId(id);
        return personEntity;
    }

    public PersonEntity generatePersonEntity(PersonPostModel personModel) {
        PersonEntity personEntity = new PersonEntity();
        BeanUtils.copyProperties(personModel, personEntity);
        return personEntity;
    }

    public static PersonModel generatePersonModel(PersonEntity personEntity) {
        PersonModel personModel = new PersonModel();
        BeanUtils.copyProperties(personEntity, personModel);
        log.info("Returning Person Model {}",personModel);
        return personModel;
    }

    public Metadata generateMetadata(Page personEntityPage) {
        Metadata metadata = new Metadata();
        metadata.setPage(personEntityPage.getNumber());
        metadata.setSize(personEntityPage.getNumberOfElements());
        metadata.setTotalElements(personEntityPage.getTotalElements());
        metadata.setTotalPages(personEntityPage.getTotalPages());
        return metadata;
    }
}
