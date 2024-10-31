package com.example.lab8_20202396.Controllers;

import com.example.lab8_20202396.Entities.Estudiante;
import com.example.lab8_20202396.Repositories.EstudianteRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping(value = {"list", ""})
    public List<Estudiante> getEstudiantes(
            @RequestParam(value = "facultad", required = false) String facultad) {
        return estudianteRepository.findByFacultadOrderByGpaDesc(facultad);
    }

    // CREAR
    /*
    @PostMapping(value = {"", "/"})
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Estudiante estudiante,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        estudianteRepository.save(estudiante);
        if (fetchId) {
            responseJson.put("id", estudiante.getId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }*/
    @PostMapping("/add")
    public Estudiante addEstudiante(@RequestBody Estudiante nuevoEstudiante) {
        estudianteRepository.save(nuevoEstudiante);

        actualizarPosiciones(nuevoEstudiante.getFacultad());

        return nuevoEstudiante;
    }

    // Método para actualizar las posiciones en la clasificación
    private void actualizarPosiciones(String facultad) {

        List<Estudiante> estudiantes = estudianteRepository.findByFacultadOrderByGpaDesc(facultad);

        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante estudiante = estudiantes.get(i);
            estudiante.setPosicion(i + 1);
            estudianteRepository.save(estudiante);
        }
    }

    @PutMapping("/{id}/actualizarGpa")
    public ResponseEntity<String> actualizarGpa(@PathVariable Integer id, @RequestParam Float nuevoGpa) {
        // Buscar el estudiante por ID
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        estudiante.setGpa(nuevoGpa);
        estudianteRepository.save(estudiante);

        recalcularPosiciones(estudiante.getFacultad());

        return ResponseEntity.ok("GPA actualizado y posiciones recalculadas");
    }

    // Método auxiliar para recalcular posiciones
    private void recalcularPosiciones(String facultad) {
        List<Estudiante> estudiantes = estudianteRepository.findByFacultadOrderByGpaDesc(facultad);

        // Actualizar la posición de cada estudiante
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            est.setPosicion(i + 1);
            estudianteRepository.save(est);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("id") String idStr){

        try{
            int id = Integer.parseInt(idStr);

            HashMap<String, Object> rpta = new HashMap<>();

            Optional<Estudiante> byId = estudianteRepository.findById(id);
            String facultad = byId.get().getFacultad();
            if(byId.isPresent()){
                estudianteRepository.deleteById(id);
                // Recalcular las posiciones de los estudiantes en la misma facultad
                recalcularPosiciones(facultad);
                rpta.put("result","ok");
            }else{
                rpta.put("result","no ok");
                rpta.put("msg","el ID enviado no existe");
            }

            return ResponseEntity.ok(rpta);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
