package com.itmo.mpa.entity;


import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "Name", nullable = false)
    private String name;

    public Disease() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }
}
