package com.example.lab8_20202396.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Estudiante {
    @Id
    @Column(name = "idEstudiante", nullable = false)
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
    @Column(name = "numCreditosTerminados", nullable = false)
    private Short numCreditosTerminados;

}