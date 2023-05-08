package com.example.dentista.model;

public class Cliente {

    private String nombre;
    private String dni;
    private String telefono;
    private String fechaNacimiento;

    public Cliente(String nombre, String dni, String telefono, String fechaNacimiento) {
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }
}
