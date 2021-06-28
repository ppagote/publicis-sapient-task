package com.uk.org.ps.publicissapienttask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "user")
@DiscriminatorValue("user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    @Email
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    /*@Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;*/

}
