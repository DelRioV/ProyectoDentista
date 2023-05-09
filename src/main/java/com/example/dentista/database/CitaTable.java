package com.example.dentista.database;

import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;

import java.sql.Connection;
import java.sql.Statement;

public class CitaTable {

    public static boolean nuevaCita(Cita cita, Connection con) {
        boolean registrado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("INSERT INTO citas VALUES ('" + cita.getFechaCita()
                    + "','" + cita.getDniCliente() + "','" + cita.getDescripcion() + "','" + cita.getHora()
                    + "','" + cita.getDuracionMin() + "')");
            if (updated == 1) {
                registrado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return registrado;
    }
}
