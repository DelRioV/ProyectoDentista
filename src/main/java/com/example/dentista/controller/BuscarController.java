package com.example.dentista.controller;

import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BuscarController {

    public static ArrayList<String> filtrarNombre(String texto) {
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

    public static ArrayList<Cliente> filtrarCliente(String texto) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        DataBaseConnection dataB = new DataBaseConnection();
        Connection connection = dataB.getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE nombre LIKE '%" + texto + "%';");
            while (rs.next()) {
                clientes.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
