package com.prueba_videojuegos.prueba_videojuegos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "videojuego")
public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre", nullable = false, length = 45)
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 45, message = "nombre inválido")
    private String nombre;
    @NotNull(message = "El precio no debe ser nulo")
    @Min(value = 1, message = "El precio debe ser mayor de 0")
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "fabricante", nullable = false, length = 45)
    @NotEmpty(message = "El fabricante no puede estar vacío")
    @Size(min = 1, max = 45, message = "fabricante inválido")
    private String fabricante;
}
