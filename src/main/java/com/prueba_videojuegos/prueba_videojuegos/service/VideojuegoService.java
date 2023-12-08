package com.prueba_videojuegos.prueba_videojuegos.service;

import com.prueba_videojuegos.prueba_videojuegos.model.Videojuego;
import com.prueba_videojuegos.prueba_videojuegos.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoService {
    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public List<Videojuego> getVideojuegoByAndAtributo(String nombre, String fabricante) {
        return videojuegoRepository.findByNombreAndFabricante(nombre, fabricante);
    }

    public List<Videojuego> getVideojuegoByOrAtributo(String nombre, String fabricante) {
        return videojuegoRepository.findByNombreOrFabricante(nombre, fabricante);
    }

    public Optional<Videojuego> getVideojuegoById(int id) {
        return videojuegoRepository.findById(id);
    }

    public List<Videojuego> getAllVideojuegos() {
        return videojuegoRepository.findAll();
    }

    public boolean nuevoVideojuego(Videojuego nuevoVideojuego) {
        try {
            videojuegoRepository.save(nuevoVideojuego);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void guardarVideojuego(Videojuego videojuego) throws Exception {
        videojuegoRepository.save(videojuego);
    }
}