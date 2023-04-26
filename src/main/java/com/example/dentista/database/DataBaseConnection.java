package com.example.dentista.database;
import java.sql.*;
/**
 *
 * @author Pablo Salvador Del Río Vergara
 * @date 18/11/2022
 * @version 1.0
 *
 *          Clase que se encarga de realizar la conexión con la base de datos
 *          SQL y devolverla
 *
 */
public class DataBaseConnection {

    private Connection connection;

    /**
     * Constructor de la clase que crea la conexión
     */
    public DataBaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // establecemos la conexion con la BD
            connection = DriverManager.getConnection("jdbc:mysql://localhost/dentist", "root", "");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha podido conectar con la base de datos.");

        }
    }

    /**
     * Método que devuelve la conexión
     *
     * @return Connection -> La conexión con la base de datos
     */
    public Connection getConnection() {
        return connection;
    }

}
