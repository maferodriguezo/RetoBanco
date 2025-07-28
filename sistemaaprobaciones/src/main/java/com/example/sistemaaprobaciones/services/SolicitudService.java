package com.example.sistemaaprobaciones.services;

import com.example.sistemaaprobaciones.dtos.AccionSolicitudDto;
import com.example.sistemaaprobaciones.dtos.SolicitudDto;
import com.example.sistemaaprobaciones.entities.Solicitud;
import com.example.sistemaaprobaciones.entities.Historico;
import com.example.sistemaaprobaciones.repositories.SolicitudRepository;
import com.example.sistemaaprobaciones.repositories.HistoricoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final HistoricoRepository historicoRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, HistoricoRepository historicoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional
    public Solicitud crearSolicitud(SolicitudDto solicitudDto) {
        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(solicitudDto.getTitulo());
        solicitud.setDescripcion(solicitudDto.getDescripcion());
        solicitud.setTipoSolicitud(solicitudDto.getTipoSolicitud());
        solicitud.setSolicitante(solicitudDto.getSolicitante());
        solicitud.setResponsable(solicitudDto.getResponsable());
        solicitud.setEstado("PENDIENTE");
        solicitud.setFechaCreacion(LocalDateTime.now());
        
        Solicitud savedSolicitud = solicitudRepository.save(solicitud);
        
        // Registrar histórico de creación
        Historico historico = new Historico();
        historico.setAccion("CREACIÓN");
        historico.setUsuario(solicitudDto.getSolicitante());
        historico.setComentario("Solicitud creada");
        historico.setFechaAccion(LocalDateTime.now());
        historico.setSolicitud(savedSolicitud);
        historicoRepository.save(historico);
        
        return savedSolicitud;
    }

    public List<Solicitud> obtenerSolicitudesPorSolicitante(String solicitante) {
        return solicitudRepository.findBySolicitante(solicitante);
    }

    public List<Solicitud> obtenerSolicitudesPendientesPorResponsable(String responsable) {
        return solicitudRepository.findByResponsableAndEstado(responsable, "PENDIENTE");
    }

    public Solicitud obtenerSolicitudPorId(UUID id) {
        return solicitudRepository.findById(id).orElse(null);
    }

    public List<Solicitud> obtenerTodasLasSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Transactional
    public Solicitud aprobarSolicitud(UUID id, AccionSolicitudDto accionDto) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow();
        solicitud.setEstado("APROBADO");
        solicitudRepository.save(solicitud);
        
        // Registrar histórico de aprobación
        Historico historico = new Historico();
        historico.setAccion("APROBACIÓN");
        historico.setUsuario(accionDto.getUsuario());
        historico.setComentario(accionDto.getComentario());
        historico.setFechaAccion(LocalDateTime.now());
        historico.setSolicitud(solicitud);
        historicoRepository.save(historico);
        
        return solicitud;
    }

    @Transactional
    public Solicitud rechazarSolicitud(UUID id, AccionSolicitudDto accionDto) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow();
        solicitud.setEstado("RECHAZADO");
        solicitudRepository.save(solicitud);
        
        // Registrar histórico de rechazo
        Historico historico = new Historico();
        historico.setAccion("RECHAZO");
        historico.setUsuario(accionDto.getUsuario());
        historico.setComentario(accionDto.getComentario());
        historico.setFechaAccion(LocalDateTime.now());
        historico.setSolicitud(solicitud);
        historicoRepository.save(historico);
        
        return solicitud;
    }
}