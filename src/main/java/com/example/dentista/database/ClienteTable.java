package com.example.dentista.database;

import com.example.dentista.model.Cliente;

import java.sql.Connection;
import java.sql.Statement;

public class ClienteTable {

    public static boolean nuevoCliente(Cliente cliente, Connection con) {
        boolean registrado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("INSERT INTO clientes VALUES ('" + cliente.getNombre()
                    + "','" + cliente.getDni() + "','" + cliente.getTelefono() + "','" + cliente.getFechaNacimiento()
                    + "')");
            if (updated == 1) {
                registrado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return registrado;
    }

    public static boolean modificarCliente(Cliente cliente, Connection con, String nombreOriginal) {
        boolean modificado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("UPDATE clientes SET nombre = '" + cliente.getNombre() + "',dni = '"
                    + cliente.getDni() + "',telefono = '" + cliente.getTelefono() + "',nacimiento = '"
                    + cliente.getFechaNacimiento() + "' WHERE nombre = '" + nombreOriginal + "' ;");
            if (updated == 1) {
                modificado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return modificado;
    }
}
