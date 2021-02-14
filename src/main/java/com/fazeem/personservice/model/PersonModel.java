package com.fazeem.personservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class PersonModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String favoriteColour;
}
