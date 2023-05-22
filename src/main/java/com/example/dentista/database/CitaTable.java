package com.example.dentista.database;

import com.example.dentista.model.Cita;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class CitaTable {

    public static boolean nuevaCita(Cita cita, Connection con) {
        boolean registrado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("INSERT INTO citas VALUES ('" + cita.getFechaCita()
                    + "','" + cita.getDniCliente() + "','" + cita.getDescripcion() + "','" + cita.getHoraInicio()
                    + "','" + cita.getHoraFin() + "')");
            if (updated == 1) {
                registrado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return registrado;
    }

    public static ArrayList<String> sacarFechasHoras(LocalDate fecha, Connection con, String nombre) {
        ArrayList<String> fechashoras = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT hora_inicio,hora_fin FROM citas WHERE fecha_cita = '" + fecha + "' " +
                    "AND dni_cliente <> (SELECT dni FROM clientes WHERE nombre = '" + nombre + "')");
            while (rs.next()) {
                fechashoras.add(rs.getString(1));
                fechashoras.add(rs.getString(2));
            }
            con.close();
        } catch (Exception e) {

        }
        return fechashoras;
    }

    public static ArrayList<String> sacarFechasHoras(LocalDate fecha, Connection con) {
        ArrayList<String> fechashoras = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT hora_inicio,hora_fin FROM citas WHERE fecha_cita = '" + fecha + "'");
            while (rs.next()) {
                fechashoras.add(rs.getString(1));
                fechashoras.add(rs.getString(2));
            }
            con.close();
        } catch (Exception e) {

        }
        return fechashoras;
    }

    public static ArrayList<String> sacarCitasNombre(String nombreCliente, Connection con) {
        ArrayList<String> citasNombre = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT fecha_cita,nombre FROM citas,clientes " +
                    "WHERE dni_cliente = (SELECT dni FROM clientes WHERE nombre = '" + nombreCliente + "') AND dni = dni_cliente");
            while (rs.next()) {
                citasNombre.add(rs.getString(1) + " || " + rs.getString(2));
            }
            con.close();
        } catch (Exception e) {

        }
        return citasNombre;
    }

    public static boolean modificarCita(Cita cita, Connection con, String nombreOriginal) {
        boolean modificado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("UPDATE citas SET fecha_cita = '" + cita.getFechaCita() + "',dni_cliente = '"
                    + cita.getDniCliente() + "',descripcion = '" + cita.getDescripcion() + "',hora_inicio = '"
                    + cita.getHoraInicio() + "',hora_fin = '" + cita.getHoraFin() +
                    "' WHERE dni_cliente = (SELECT dni FROM clientes WHERE nombre = '" + nombreOriginal + "') ;");
            if (updated == 1) {
                modificado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return modificado;
    }

    public static boolean eliminarCita(String nombre, Connection con, String fecha) {
        boolean eliminado = false;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT dni FROM clientes " + " WHERE nombre = '" + nombre + "'");
            rs.next();
            String dni = rs.getString(1);
            int filasUpdated = st.executeUpdate("DELETE FROM citas WHERE fecha_cita = '" + fecha.trim() + "' AND dni_cliente = '" + dni + "'");
            if (filasUpdated != 0) {
                eliminado = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eliminado;
    }
}
