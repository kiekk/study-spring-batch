package io.springbatch.studyspringbatch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @OneToOne(mappedBy = "customer")
    private Address address;

}
