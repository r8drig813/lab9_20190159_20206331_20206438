package com.example.lab9.repository;

import com.example.lab9.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
}
