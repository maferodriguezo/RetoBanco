package com.example.sistemaaprobaciones.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    private String titulo;
    private String descripcion;
    private String tipoSolicitud;
    private String solicitante;
    private String responsable;
    private String estado;
    private LocalDateTime fechaCreacion;
    
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<Historico> historicos;
}