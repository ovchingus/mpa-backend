package com.itmo.mpa.entity;


import javax.persistence.*;

@Entity
public class States {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StateId", nullable = false, updatable = false)
    private long id;

    @Column(name = "StateName", nullable = false)
    private String name;

    @Column(name = "DiseaseId")
    private long diseaseId;

}
