package com.example.sistemaaprobaciones.repositories;

import com.example.sistemaaprobaciones.entities.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findBySolicitudId(UUID solicitudId);
}