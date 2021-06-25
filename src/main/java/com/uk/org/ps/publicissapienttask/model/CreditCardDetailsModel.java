package com.uk.org.ps.publicissapienttask.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cc_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //@CreationTimestamp
    @Column(name = "created_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(255)")
    private String userName;

    @Column(name = "cc_number", nullable = false, columnDefinition = "varchar(255)")
    private String ccNumber;

    @Column(name = "cc_limit", nullable = false, columnDefinition = "integer")
    private int ccLimit;

    @Column(name = "cc_balance", columnDefinition = "integer default 0")
    private final int ccBalance = 0;
}
