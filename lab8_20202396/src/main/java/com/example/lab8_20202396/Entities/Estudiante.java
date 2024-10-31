package com.example.lab8_20202396.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestudiante", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @Column(name = "gpa", nullable = false)
    private Float gpa;

    @Size(max = 45)
    @NotNull
    @Column(name = "facultad", nullable = false, length = 45)
    private String facultad;

    @NotNull
    @Column(name = "numcreditosterminados", nullable = false)
    private Short numCreditosTerminados;

    @Column(name ="posicion")
    private Integer posicion;

}