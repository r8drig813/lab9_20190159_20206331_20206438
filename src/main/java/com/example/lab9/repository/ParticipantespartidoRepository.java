package com.example.lab9.repository;

import com.example.lab9.entity.Equipo;
import com.example.lab9.entity.Participante;
import com.example.lab9.entity.Participantespartido;
import com.example.lab9.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ParticipantespartidoRepository extends JpaRepository<Participantespartido, Integer> {


}
