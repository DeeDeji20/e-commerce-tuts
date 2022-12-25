package com.deedeji.ecommerce.data.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer extends AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Cart cart;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Set<Address> address = new HashSet<>();
}
