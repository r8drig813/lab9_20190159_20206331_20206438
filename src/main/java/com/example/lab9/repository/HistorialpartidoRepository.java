package com.example.lab9.repository;

import com.example.lab9.entity.Historialpartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HistorialpartidoRepository extends JpaRepository<Historialpartido, Integer> {
}
