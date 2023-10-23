package com.example.lab9.repository;

import com.example.lab9.entity.Equipo;
import com.example.lab9.entity.Participante;
import com.example.lab9.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    //List<Participante> findAllByPartidoIdpartido(Partido partido);

    @Query(value = "SELECT * FROM participante p " +
            "INNER JOIN participantespartido pp " +
            "ON p.idparticipante = pp.participante_idparticipante " +
            "WHERE pp.partido_idpartido = ?1", nativeQuery = true)
    List<Participante> listarParticipantes(Integer idPartido);

    @Query(value = "SELECT * FROM participante p " +
            "INNER JOIN participantespartido pp " +
            "ON p.idparticipante = pp.participante_idparticipante " +
            "WHERE pp.partido_idpartido = ?1 AND p.equipo = ?2", nativeQuery = true)
    List<Participante> listarParticipantesEquipo(Integer idPartido,Integer idEquipo);

}
