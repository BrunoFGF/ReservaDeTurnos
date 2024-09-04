package com.hd.ProyectoIntegrador.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "domicilios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;
    @OneToOne(mappedBy = "domicilio")
    private Paciente paciente;
}
