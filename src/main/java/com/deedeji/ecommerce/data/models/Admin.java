package com.deedeji.ecommerce.data.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Admin extends AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
