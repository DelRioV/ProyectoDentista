package com.example.dentista.model;

import java.time.LocalDate;

/**
 * Clase que permite almacenar los datos de las citas
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 15/05/2023
 */
public class Cita {
    private LocalDate fechaCita;
    private String dniCliente;
    private String descripcion;
    private String horaInicio;
    private String horaFin;

    /**
     * Constructor
     *
     * @param fechaCita
     * @param dniCliente
     * @param descripcion
     * @param horaInicio
     * @param horaFin
     */
    public Cita(LocalDate fechaCita, String dniCliente, String descripcion, String horaInicio, String horaFin) {
        this.setFechaCita(fechaCita);
        this.setDniCliente(dniCliente);
        this.setDescripcion(descripcion);
        this.setHoraInicio(horaInicio);
        this.setHoraFin(horaFin);
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
