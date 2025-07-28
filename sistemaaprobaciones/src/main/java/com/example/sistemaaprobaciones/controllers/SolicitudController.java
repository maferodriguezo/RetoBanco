package com.example.sistemaaprobaciones.controllers;

import com.example.sistemaaprobaciones.dtos.AccionSolicitudDto;
import com.example.sistemaaprobaciones.dtos.SolicitudDto;
import com.example.sistemaaprobaciones.entities.Solicitud;
import com.example.sistemaaprobaciones.services.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody SolicitudDto solicitudDto) {
        Solicitud solicitud = solicitudService.crearSolicitud(solicitudDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(solicitud.getId())
                .toUri();
        return ResponseEntity.created(location).body(solicitud);
    }

    @GetMapping("/mis-solicitudes")
    public List<Solicitud> obtenerMisSolicitudes(@RequestParam String solicitante) {
        return solicitudService.obtenerSolicitudesPorSolicitante(solicitante);
    }

    @GetMapping("/pendientes")
    public List<Solicitud> obtenerSolicitudesPendientes(@RequestParam String responsable) {
        return solicitudService.obtenerSolicitudesPendientesPorResponsable(responsable);
    }

    @GetMapping("/{id}")
    public Solicitud obtenerSolicitud(@PathVariable UUID id) {
        return solicitudService.obtenerSolicitudPorId(id);
    }

    @GetMapping
    public List<Solicitud> obtenerTodasLasSolicitudes() {
        return solicitudService.obtenerTodasLasSolicitudes();
    }

    @PostMapping("/{id}/aprobar")
    public Solicitud aprobarSolicitud(@PathVariable UUID id, @RequestBody AccionSolicitudDto accionDto) {
        return solicitudService.aprobarSolicitud(id, accionDto);
    }

    @PostMapping("/{id}/rechazar")
    public Solicitud rechazarSolicitud(@PathVariable UUID id, @RequestBody AccionSolicitudDto accionDto) {
        return solicitudService.rechazarSolicitud(id, accionDto);
    }
}