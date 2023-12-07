package com.prueba_videojuegos.prueba_videojuegos.repository;

import com.prueba_videojuegos.prueba_videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {
    List<Videojuego> findByNombre(String nombre);
    List<Videojuego> findByFabricante(String fabricante);
    List<Videojuego> findByNombreAndFabricante(String nombre, String fabricante);
    List<Videojuego> findByNombreOrFabricante(String nombre, String fabricante);
}