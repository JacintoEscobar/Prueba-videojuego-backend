package com.prueba_videojuegos.prueba_videojuegos.controller;

import com.prueba_videojuegos.prueba_videojuegos.exceptions.ListaVaciaException;
import com.prueba_videojuegos.prueba_videojuegos.model.Videojuego;
import com.prueba_videojuegos.prueba_videojuegos.service.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/videojuegos")
public class VideojuegoController {
    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<?> getAllVideojuegos(@RequestParam(value = "term", defaultValue = "") String nombre) {
        if (nombre.isEmpty()) {
            List<Videojuego> videojuegos = videojuegoService.getAllVideojuegos();
            return new ResponseEntity<>(videojuegos, HttpStatus.OK);
        }

        try {
            List<Videojuego> videojuegosFiltrados = videojuegoService.getAllVideojuegoByNombre(nombre);
            return ResponseEntity.ok(videojuegosFiltrados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideojuegoById(@PathVariable int id) {
        try {
            Optional<Videojuego> videojuego = videojuegoService.getVideojuegoById(id);
            if (videojuego.isEmpty()) {
                throw new NoSuchElementException("Videojuego no encontrado");
            }
            return new ResponseEntity<>(videojuego.get(), HttpStatus.FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getVideojuegosByAtributo(@RequestParam(required = false, defaultValue = "") String nombre, @RequestParam(required = false, defaultValue = "") String fabricante, @RequestParam(required = false, defaultValue = "AND") String tipoFiltrado) {
        try {
            List<Videojuego> videojuegos = null;
            if (tipoFiltrado.equals("AND")) {
                videojuegos = videojuegoService.getVideojuegoByAndAtributo(nombre, fabricante);
            } else if (tipoFiltrado.equals("OR")) {
                videojuegos = videojuegoService.getVideojuegoByOrAtributo(nombre, fabricante);
            }

            if (videojuegos.isEmpty()) {
                throw new ListaVaciaException("Ningún videojuego filtrado");
            }
            return new ResponseEntity<>(videojuegos, HttpStatus.FOUND);
        } catch (ListaVaciaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/nuevo", consumes = "application/json")
    public ResponseEntity<String> nuevoVideojuego(@Valid @RequestBody Videojuego nuevoVideojuego, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder mensajeError = new StringBuilder();
            result.getFieldErrors().forEach(error -> {
                mensajeError.append(error.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.badRequest().body("Datos incorrectos: " + mensajeError);
        }
        if (!videojuegoService.nuevoVideojuego(nuevoVideojuego)) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al crear el videojuego");
        }
        return new ResponseEntity<>("Videojuego creado exitosamente", HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}/actualizar", consumes = "application/json")
    public ResponseEntity<String> patchUpdateVideojuego(@PathVariable int id, @RequestBody Map<?, Object> camposActualizados) {
        if (videojuegoService.getVideojuegoById(id).isEmpty()) {
            return new ResponseEntity<>("Videojuego no encontrado", HttpStatus.NOT_FOUND);
        }

        if (camposActualizados.isEmpty()) {
            return new ResponseEntity<>("No se proporcionó ningún dato para actualizar", HttpStatus.BAD_REQUEST);
        }

        Videojuego videojuegoExistente = videojuegoService.getVideojuegoById(id).get();

        if (camposActualizados.containsKey("nombre") && !camposActualizados.get("nombre").toString().isBlank()) {
            videojuegoExistente.setNombre((String) camposActualizados.get("nombre"));
        }

        if (camposActualizados.containsKey("precio") && camposActualizados.get("precio") != null) {
            videojuegoExistente.setPrecio((double) camposActualizados.get("precio"));
        }

        if (camposActualizados.containsKey("fabricante") && !camposActualizados.get("fabricante").toString().isBlank()) {
            videojuegoExistente.setFabricante((String) camposActualizados.get("fabricante"));
        }

        try {
            videojuegoService.guardarVideojuego(videojuegoExistente);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/actualizar", consumes = "application/json")
    public ResponseEntity<String> putUpdateVideojuego(@PathVariable int id, @Valid @RequestBody Videojuego videojuegoModificado, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder mensajeError = new StringBuilder();
            result.getFieldErrors().forEach(error -> {
                mensajeError.append("Error: " + error.getDefaultMessage() + "\n");
            });
            return ResponseEntity.badRequest().body("--Datos incorrectos--\n " + mensajeError);
        }

        try {
            Videojuego videojuegoExistente = videojuegoService.getVideojuegoById(id).get();

            videojuegoExistente.setId(id);
            videojuegoExistente.setNombre(videojuegoModificado.getNombre());
            videojuegoExistente.setPrecio(videojuegoModificado.getPrecio());
            videojuegoExistente.setFabricante(videojuegoModificado.getFabricante());

            videojuegoService.guardarVideojuego(videojuegoModificado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok("Videjouego actualizado correctamente.");
    }

    @DeleteMapping(path = "/{id}/eliminar", consumes = "application/json")
    public ResponseEntity<String> deleteVideojuego(@PathVariable int id) {
        try {
            Videojuego videojuego = videojuegoService.getVideojuegoById(id).get();
            videojuegoService.deleteVideojuego(videojuego);
            return new ResponseEntity<>("Videojuego eliminado correctamente", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}