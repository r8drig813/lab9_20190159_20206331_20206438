package com.example.lab9.repository;

import com.example.lab9.entity.Participantespartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ParticipantespartidoRepository extends JpaRepository<Participantespartido, Integer> {
}
