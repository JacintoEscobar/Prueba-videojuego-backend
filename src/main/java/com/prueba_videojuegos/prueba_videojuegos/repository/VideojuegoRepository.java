package com.prueba_videojuegos.prueba_videojuegos.repository;

import com.prueba_videojuegos.prueba_videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {
}
