package com.microservices.bits.assignment.authservice.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="users")
@Entity
@Builder
public class User {
    @Id
    @Column(unique = true,length = 200)
    private String mobileno;
    private String empname;
    private String empid;
    private String emptype;
    private String secretkey;
}

