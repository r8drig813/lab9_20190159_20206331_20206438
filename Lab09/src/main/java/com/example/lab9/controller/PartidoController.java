package com.example.lab9.controller;

import com.example.lab9.entity.*;
import com.example.lab9.repository.*;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partido")
public class PartidoController {
    @Autowired
    HistorialpartidoRepository historialpartidoRepository;
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    ParticipantespartidoRepository participantespartidoRepository;
    @Autowired
    ParticipanteRepository participanteRepository;
    @Autowired
    EquipoRepository equipoRepository;

    // CREAR
    @PostMapping(value = {"/registro"})
    public ResponseEntity<HashMap<String, Object>> guardarPartido(
            @RequestBody Partido partido,
            @RequestParam(value = "iddeporte") String idStr,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {
        HashMap<String, Object> responseJson = new HashMap<>();
        try {
            if (idStr.isBlank() || idStr.isEmpty()) {
                System.out.println("xd");
                responseJson.put("error", "debe ingresar como parametro un idDeporte valido");
            } else {
                partidoRepository.save(partido);
                Historialpartido historialpartido = new Historialpartido();
                Deporte deporte = new Deporte();
                deporte.setId(Integer.parseInt(idStr));
                historialpartido.setPartidoIdpartido(partido);
                historialpartido.setHoraFecha(Instant.now());
                historialpartido.setDeporteIddeporte(deporte);
                historialpartidoRepository.save(historialpartido);
                if (fetchId) {
                    responseJson.put("id", partido.getId());
                }
                responseJson.put("estado", "creado");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = {"/getparticipantes/{idpartido}"})
    public ResponseEntity<HashMap<String, Object>> listaParticipante(@RequestParam(value = "idequipo", required = false) String idEquipo,
                                                                     @PathVariable("idpartido") String idPartido) {
        HashMap<String, Object> responseJson = new HashMap<>();
        try {

            Optional<Partido> optionalPartido = partidoRepository.findById(Integer.parseInt(idPartido));
            if (optionalPartido.isPresent()) {
                if (idEquipo == null || idEquipo.isBlank()) {
                    responseJson.put("Participante", participanteRepository.listarParticipantes(Integer.parseInt(idPartido)));
                    return ResponseEntity.status(HttpStatus.OK).body(responseJson);
                } else {
                    Optional<Equipo> optional = equipoRepository.findById(Integer.parseInt(idEquipo));
                    if (optional.isPresent()) {
                        responseJson.put("Participante", participanteRepository.listarParticipantesEquipo(Integer.parseInt(idPartido),
                                Integer.parseInt(idEquipo)));
                        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
                    } else {
                        responseJson.put("error", "No se encontro el equipo indicado");
                        return ResponseEntity.badRequest().body(responseJson);
                    }
                }
            } else {
                responseJson.put("error", "No se encontro el partido indicado");
                return ResponseEntity.badRequest().body(responseJson);
            }
        } catch (NumberFormatException e) {
            responseJson.put("error", "Debe ingresar solo numeros");
            return ResponseEntity.badRequest().body(responseJson);
        }
    }

    /*
    @GetMapping(value = {"/getparticipantes/{idpartido}"})
    public List<Participante> listaParticipante(@RequestParam(value = "idequipo", required = false) String idEquipo,
                                                        @PathVariable("idpartido") String idPartido) {
        try{
            Optional<Partido> optionalPartido = partidoRepository.findById(Integer.parseInt(idPartido));
            if(optionalPartido.isPresent()){
                if(idEquipo == null || idEquipo.isBlank()){
                    return participanteRepository.listarParticipantes(Integer.parseInt(idPartido));
                }else{
                    Optional<Equipo> optional = equipoRepository.findById(Integer.parseInt(idEquipo));
                    if(optional.isPresent()){
                        return participanteRepository.listarParticipantesEquipo(Integer.parseInt(idPartido),
                                Integer.parseInt(idEquipo));
                    }else {
                        return null;
                    }
                }
            }
            else {
                return null;
            }
        }catch (NumberFormatException e){
            return null;
        }
    }
    */
    //LISTAR HISTORIAL DE PARTIDOS
    @GetMapping(value = {"/gethistorialpartidos"})
    public ResponseEntity<HashMap<String, Object>> listaHistorial(@RequestParam(value = "idequipo", required = false) String idStr) {
        HashMap<String,Object> respuesta = new HashMap<>();


        try{

            Optional<Equipo> optionalEquipo = equipoRepository.findById(Integer.parseInt(idStr));
            if(optionalEquipo.isPresent()){
                respuesta.put("Historial",historialpartidoRepository.listaHistorial(Integer.parseInt(idStr)));
                return ResponseEntity.status(HttpStatus.OK).body(respuesta);

            } else {
                respuesta.put("error", "No se encontro el equipo indicado");
                return ResponseEntity.badRequest().body(respuesta);
            }
        }catch (NumberFormatException e){
            respuesta.put("Historial",historialpartidoRepository.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);        }

    }

}
