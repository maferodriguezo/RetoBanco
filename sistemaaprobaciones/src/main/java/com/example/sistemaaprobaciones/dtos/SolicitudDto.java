package com.example.sistemaaprobaciones.dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDto {
    private String titulo;
    private String descripcion;
    private String tipoSolicitud;
    private String solicitante;
    private String responsable;
}