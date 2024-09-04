package com.hd.ProyectoIntegrador.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "odontologos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricula;
    private String nombre;
    private String apellido;
}
