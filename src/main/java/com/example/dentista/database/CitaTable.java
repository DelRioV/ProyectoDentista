package com.example.dentista.database;

import com.example.dentista.model.Cita;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que contiene los métodos de acceso a la base de datos, concretamente, la tabla de citas
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 25/05/2023
 */
public class CitaTable {
    /**
     * Método que se encarga de realizar un registro nuevo
     *
     * @param cita - Cita -> la cita que se va a registrar
     * @param con  - Connection -> conexión de la base de datos
     * @return
     */
    public static boolean nuevaCita(Cita cita, Connection con) {
        boolean registrado = false;
        System.out.println(cita.getHoraInicio());
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

    /**
     * Método que permite sacar las fechas y horas de las citas de un cliente
     *
     * @param fecha  - LocalDate -> fecha del día
     * @param con    - Connection -> conexión de la base de datos
     * @param nombre - String -> nombre del cliente
     * @return ArrayList con los datos que ha encontrado
     */
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

    /**
     * Método que permite sacar las fechas y horas de las citas
     *
     * @param fecha - LocalDate -> fecha del día
     * @param con   - Connection -> conexión de la base de datos
     * @return ArrayList con los datos que ha encontrado
     */
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

    /**
     * Método que saca las citas de un cliente
     *
     * @param nombreCliente - String -> nombre del cliente
     * @param con           - Connection -> conexión de la base de datos
     * @return ArrayList que devuelve con las citas y los nombres de un cliente
     */
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

    /**
     * Método que permite modificar las citas
     *
     * @param cita        - Cita -> La cita modificada
     * @param con         - Connection -> conexión de la base de datos
     * @param fechaNombre - String -> fecha y nombre de la cita a cambiar
     * @return boolean -> true - se ha realizado correctamente
     * -> false - no se ha podido realizar
     */
    public static boolean modificarCita(Cita cita, Connection con, String fechaNombre) {
        boolean modificado = false;
        try {
            Statement st = con.createStatement();
            int updated = st.executeUpdate("UPDATE citas SET fecha_cita = '" + cita.getFechaCita() + "',dni_cliente = '"
                    + cita.getDniCliente() + "',descripcion = '" + cita.getDescripcion() + "',hora_inicio = '"
                    + cita.getHoraInicio() + "',hora_fin = '" + cita.getHoraFin() +
                    "' WHERE dni_cliente = (SELECT dni FROM clientes WHERE nombre = '" +
                    fechaNombre.substring(fechaNombre.indexOf("|") + 2, fechaNombre.length()).trim() + "') AND fecha_cita = '"
                    + fechaNombre.substring(0, fechaNombre.indexOf("|")).trim() + "';");
            if (updated == 1) {
                modificado = true;
            }
            con.close();
        } catch (Exception e) {

        }
        return modificado;
    }

    /**
     * Método que permite eliminar las citas
     *
     * @param nombre - String -> nombre del cliente
     * @param con    - Connection -> conexión de la base de datos
     * @param fecha  - String -> fecha de la cita
     * @return boolean -> true - se ha realizado correctamente
     * -> false - no se ha podido realizar
     */
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

    /**
     * Método que permite tomar las citas
     *
     * @param con   -  Connection -> conexión de la base de datos
     * @param fecha - String -> la fecha de las citas
     * @return ArrayList con todas las citas de una fecha
     */
    public static ArrayList<Cita> tomarCitas(Connection con, String fecha) {
        ArrayList<Cita> citas = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM citas WHERE fecha_cita LIKE '%" + fecha + "%';");
            while (rs.next()) {
                Cita cita = new Cita(rs.getDate(1).toLocalDate(), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                citas.add(cita);
            }
            con.close();
        } catch (Exception e) {

        }
        return citas;
    }
}
