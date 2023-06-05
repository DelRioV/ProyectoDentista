package com.example.dentista.database;

import com.example.dentista.model.Cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase que contiene los métodos de acceso a la base de datos, concretamente, la tabla de citas
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 25/05/2023
 */
public class ClienteTable {
    /**
     * Método que permite el registro de un nuevo cliente
     *
     * @param cliente - Cliente -> el cliente que se va a registrar
     * @param con     -  Connection -> conexión de la base de datos
     * @return boolean -> true - se ha realizado correctamente
     * -> false - no se ha podido realizar
     */
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

    /**
     * Método que permite modificar los clientes
     *
     * @param cliente        - Cliente -> el cliente modificado
     * @param con            -  Connection -> conexión de la base de datos
     * @param nombreOriginal - String -> el nombre del cliente original
     * @return boolean -> true - se ha realizado correctamente
     * -> false - no se ha podido realizar
     */
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

    /**
     * Método que permite eliminar los clientes
     *
     * @param con    -  Connection -> conexión de la base de datos
     * @param nombre - String -> nombre del cliente a eliminar
     * @return boolean -> true - se ha realizado correctamente
     * -> false - no se ha podido realizar
     */
    public static boolean eliminarCliente(Connection con, String nombre) {
        boolean elimniado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("DELETE FROM clientes WHERE nombre = '" + nombre + "';");
            if (updated == 1) {
                elimniado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return elimniado;
    }

    /**
     * Método que permite tomar un cliente a partir de su dni
     *
     * @param con -  Connection -> conexión de la base de datos
     * @param dni - String -> el dni del cliente a buscar
     * @return Cliente -> el cliente que se ha encontrado en la base de datos
     */
    public static Cliente tomarCliente(Connection con, String dni) {
        Cliente cliente = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE dni = '" + dni + "'");
            while (rs.next()) {
                cliente = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            con.close();
        } catch (Exception e) {

        }
        return cliente;
    }
}
