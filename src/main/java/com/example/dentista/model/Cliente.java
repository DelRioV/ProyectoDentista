package com.example.dentista.model;

/**
 * Clase que permite almacenar los datos de los clientes
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 15/05/2023
 */
public class Cliente {

    private String nombre;
    private String dni;
    private String telefono;
    private String fechaNacimiento;

    /**
     * Constructor
     *
     * @param nombre
     * @param dni
     * @param telefono
     * @param fechaNacimiento
     */
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
