package com.fazeem.personservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonPostModel {
    private String firstName;
    private String lastName;
    private Integer age;
    private String favoriteColour;
}
