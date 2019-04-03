package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "drafts")
class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @OneToOne
    @JoinColumn(name = "status_id")
    lateinit var patient: Patient
}