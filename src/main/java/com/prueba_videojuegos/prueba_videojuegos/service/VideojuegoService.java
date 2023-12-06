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

    public Optional<Videojuego> getVideojuegoById(int id) {
        return videojuegoRepository.findById(id);
    }

    public List<Videojuego> getAllVideojuegos() {
        return videojuegoRepository.findAll();
    }
}
