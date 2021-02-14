package com.fazeem.personservice.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PersonResponseContainerModels {
    List<PersonModel> personModels;
    Metadata metadata;
}
