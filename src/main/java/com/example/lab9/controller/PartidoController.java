package com.example.lab9.controller;

import com.example.lab9.entity.Participante;
import com.example.lab9.entity.Partido;
import com.example.lab9.repository.ParticipanteRepository;
import com.example.lab9.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partido")
public class PartidoController {

    final
    PartidoRepository partidoRepository;
    final
    ParticipanteRepository participanteRepository;

    public PartidoController(PartidoRepository partidoRepository, ParticipanteRepository participanteRepository) {
        this.partidoRepository = partidoRepository;
        this.participanteRepository = participanteRepository;
    }
    //Listar Partidos
    @GetMapping(value = {"/getparticipantes"})
    public List<Participante> listaParticipantes() {
        return participanteRepository.findAll();
    }git add ,

    // Obtener partidos
    /*@GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarPartido(@PathVariable("id") String idStr) {


        try {
            int id = Integer.parseInt(idStr);
            Optional<Partido> byId = partidoRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("Partido", byId.get());
            } else {
                respuesta.put("result", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }*/

    //Registrar partido
    @PostMapping(value = {"", "/registro"})
    public ResponseEntity<HashMap<String, Object>> guardarPartido(
            @RequestBody Partido partido,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        partidoRepository.save(partido);
        if (fetchId) {
            responseJson.put("id", partido.getId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    //Obtener Participante
    /*@GetMapping(value = "/getparticipantes1")
    public ResponseEntity<HashMap<String, Object>> buscarPartipantes(@PathVariable("id") String idStr) {


        try{
            int id = Integer.parseInt(idStr);
            //Optional<Partido> byId = partidoRepository.findById(id);
            Optional<Participante> byId = participanteRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("Participante", byId.get());
            } else {
                respuesta.put("result", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }*/


}
