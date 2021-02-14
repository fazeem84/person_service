package com.fazeem.personservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Metadata {
    Integer size;
    Long totalElements;
    Integer totalPages;
    Integer page;
}
