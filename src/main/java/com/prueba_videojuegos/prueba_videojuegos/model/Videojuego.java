package com.prueba_videojuegos.prueba_videojuegos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "videojuego")
public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private float precio;
}
