package com.example.sistemaaprobaciones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accion;
    private String usuario;
    private String comentario;
    private LocalDateTime fechaAccion;
    
    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;
}