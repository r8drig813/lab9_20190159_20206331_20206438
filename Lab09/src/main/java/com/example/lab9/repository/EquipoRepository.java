package com.example.lab9.repository;

import com.example.lab9.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
}
