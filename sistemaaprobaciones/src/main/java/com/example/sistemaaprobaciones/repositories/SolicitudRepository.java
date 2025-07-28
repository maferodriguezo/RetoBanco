package com.example.sistemaaprobaciones.repositories;

import com.example.sistemaaprobaciones.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SolicitudRepository extends JpaRepository<Solicitud, UUID> {
    List<Solicitud> findBySolicitante(String solicitante);
    List<Solicitud> findByResponsableAndEstado(String responsable, String estado);
}