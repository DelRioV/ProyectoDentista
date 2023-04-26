package com.example.dentista;

import com.example.dentista.database.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        Connection connection =  new DataBaseConnection().getConnection();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE nombre LIKE 'PABLO%'");
        while (rs.next()) {
            System.out.println(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3));
            welcomeText.setText(rs.getString(1));
        }
        connection.close();
    }
}