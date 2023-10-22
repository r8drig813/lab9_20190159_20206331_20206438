package com.example.lab9.controller;

import com.example.lab9.entity.*;
import com.example.lab9.repository.EquipoRepository;
import com.example.lab9.repository.HistorialpartidoRepository;
import com.example.lab9.repository.ParticipantespartidoRepository;
import com.example.lab9.repository.PartidoRepository;
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
    EquipoRepository equipoRepository;

    // CREAR
    @PostMapping(value = {"/registro"})
    public ResponseEntity<HashMap<String, Object>> guardarPartido(
            @RequestBody Partido partido,
            @RequestParam(value = "iddeporte") String idStr,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {
        HashMap<String, Object> responseJson = new HashMap<>();
        try{
            if(idStr.isBlank() || idStr.isEmpty()){
                System.out.println("xd");
                responseJson.put("error", "debe ingresar como parametro un idDeporte valido");
            }
            else {
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
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);

    }


    @GetMapping(value = {"/getparticipantes"})
    public List<Participantespartido> listaParticipante(@RequestParam("idequipo") String idStr,
                                                        @RequestBody Partido partido) {
        HashMap<String,Object> responseJson = new HashMap<>();
        try{
            Optional<Equipo> optionalEquipo = equipoRepository.findById(Integer.parseInt(idStr));
            if(idStr.isEmpty() || idStr.isBlank()){
                participantespartidoRepository.findAllByPartidoIdpartido(partido);
            } else if (optionalEquipo.isPresent()) {
                participantespartidoRepository.findAllByPartidoIdpartido(partido);
            }
        }catch (NumberFormatException e){
            return participantespartidoRepository.findAll();


        }
        return participantespartidoRepository.findAll();
    }


    //LISTAR HISTORIAL DE PARTIDOS
    @GetMapping(value = {"/gethistorialpartidos"})
    public List<Historialpartido> listaHistorial(@RequestParam(value = "idequipo", required = false) String idStr) {


        try{
            HashMap<String,Object> respuesta = new HashMap<>();

            Optional<Equipo> optionalEquipo = equipoRepository.findById(Integer.parseInt(idStr));
            if(optionalEquipo.isEmpty()){
                respuesta.put("result", "no existe");
            } else {
                return historialpartidoRepository.listaHistorial(Integer.parseInt(idStr));
            }
        }catch (NumberFormatException e){
            return historialpartidoRepository.findAll();
        }
        return historialpartidoRepository.findAll();
    }

}
