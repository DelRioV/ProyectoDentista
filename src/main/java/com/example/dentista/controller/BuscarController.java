package com.example.dentista.controller;

import com.example.dentista.database.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BuscarController {

    public static ArrayList<String> filtrarCliente(String texto) {
        ArrayList<String> clientes = new ArrayList<>();
        DataBaseConnection dataB = new DataBaseConnection();
        Connection connection = dataB.getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre FROM clientes WHERE nombre LIKE '%" + texto + "%';");
            while (rs.next()) {
                clientes.add(rs.getString(1));
            }
            connection.close();
        } catch (Exception e) {

        }
        return clientes;
    }
}
