package com.example.lab9.repository;

import com.example.lab9.entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeporteRepository extends JpaRepository <Deporte, Integer> {
}
