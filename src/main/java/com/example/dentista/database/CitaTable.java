package com.example.dentista.database;

import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;

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
                    + "','" + cita.getDniCliente() + "','" + cita.getDescripcion() + "','" + cita.getHora().replace(":","")
                    + "','" + cita.getDuracionMin() + "')");
            if (updated == 1) {
                registrado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return registrado;
    }

    public static ArrayList<String> sacarFechasHoras(LocalDate fecha, Connection con) {
        ArrayList<String> fechashoras = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT hora,duracion_min FROM citas WHERE fecha_cita = '" + fecha + "'");
            while(rs.next()){
                fechashoras.add(rs.getString(1));
                fechashoras.add(rs.getString(2));
            }
            con.close();
        } catch (Exception e) {

        }
        return fechashoras;
    }
}
