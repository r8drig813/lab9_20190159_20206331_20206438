package com.example.lab9.repository;

import com.example.lab9.entity.Historialpartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HistorialpartidoRepository extends JpaRepository<Historialpartido, Integer> {

    @Query(value = "select * from historialpartidos h \n" +
            "inner join partido p on p.idpartido = h.partido_idpartido\n" +
            "inner join equipo e on e.idequipo = p.equipoA or e.idequipo = p.equipoB\n" +
            "where e.idequipo = ?1",nativeQuery = true)
    List<Historialpartido> listaHistorial(Integer idequipo);
}
