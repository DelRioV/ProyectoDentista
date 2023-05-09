package com.example.dentista.model;

import java.time.LocalDate;

public class Cita {
    private LocalDate fechaCita;
    private String dniCliente;
    private String descripcion;
    private String hora;
    private int duracionMin;

    public Cita(LocalDate fechaCita, String dniCliente, String descripcion, String hora, int duracionMin) {
        this.setFechaCita(fechaCita);
        this.setDniCliente(dniCliente);
        this.setDescripcion(descripcion);
        this.setHora(hora);
        this.setDuracionMin(duracionMin);
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }
}
