package com.itmo.mpa.entity;


import javax.persistence.*;

@Entity
public class Diseases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiseaseId", nullable = false, updatable = false)
    private long diseaseId;

    @Column(name = "Name", nullable = false)
    private String name;

}
