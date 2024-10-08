package com.hd.ProyectoIntegrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaAlta;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id")
    @JsonManagedReference
    private Domicilio domicilio;
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();
}
