package com.uk.org.ps.publicissapienttask.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "created_time", columnDefinition = "TIMESTAMP",
            insertable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdTime;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(255)")
    private String userName;

    @Column(name = "cc_number", nullable = false, columnDefinition = "Long")
    private Long ccNumber;

    @Column(name = "cc_limit", nullable = false, columnDefinition = "integer")
    private int ccLimit;

    @Column(name = "cc_balance", columnDefinition = "integer")
    @ColumnDefault("0")
    private int ccBalance;
}
