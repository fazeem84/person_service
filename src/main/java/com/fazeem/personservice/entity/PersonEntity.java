package com.fazeem.personservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Entity
@Table(name="person" , uniqueConstraints ={@UniqueConstraint(name="UC_person",columnNames = {"first_name", "last_name","age"})})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "age",nullable = false)
    private Integer age;
    @Column(name = "favorite_colour")
    private String favoriteColour;
}
