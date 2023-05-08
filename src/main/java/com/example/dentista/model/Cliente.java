package com.example.dentista.model;

public class Cliente {

    private String nombre;
    private String dni;
    private String telefono;
    private String fechaNacimiento;

    public Cliente(String nombre, String dni, String telefono, String fechaNacimiento) {
        this.setNombre(nombre);
        this.setDni(dni);
        this.setTelefono(telefono);
        this.setFechaNacimiento(fechaNacimiento);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
