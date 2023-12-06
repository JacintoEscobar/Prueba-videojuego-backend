package com.prueba_videojuegos.prueba_videojuegos.controller;

import com.prueba_videojuegos.prueba_videojuegos.model.Videojuego;
import com.prueba_videojuegos.prueba_videojuegos.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/videojuegos")
public class VideojuegoController {
    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<List<Videojuego>> getAllVideojuegos() {
        List<Videojuego> videojuegos = videojuegoService.getAllVideojuegos();
        return new ResponseEntity<>(videojuegos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideojuegoById(@PathVariable int id) {
        try {
            Optional<Videojuego> videojuego = videojuegoService.getVideojuegoById(id);
            if (!videojuego.isPresent()) {
                throw new NoSuchElementException("Videojuego no encontrado");
            }
            return new ResponseEntity<>(videojuego.get(), HttpStatus.FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}