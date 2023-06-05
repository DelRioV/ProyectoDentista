package com.example.dentista.controller;

import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Clase que controla las acciones para poder mandar un mensaje a través de la URL de whatsapp
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 25/05/2023
 */
public class WhatsappController {
    /**
     * Método que permite mandar un mensaje a través de whatsapp
     *
     * @param cliente - Cliente -> El cliente al que se le va a mandar un mensaje
     * @param cita    - Cita -> La cita que se va a enviar
     * @throws MalformedURLException
     */
    public static void whatsapp(Cliente cliente, Cita cita) throws MalformedURLException {
        Locale español = new Locale("es", "ES");
        URL url = new URL("https://wa.me/" + "+34" + cliente.getTelefono() + "/?" + "text=¡Buenas!%20Le%20recordamos%20que%20el%20día%20" +
                cita.getFechaCita().getDayOfMonth() + "%20de%20" + cita.getFechaCita().getMonth().getDisplayName(TextStyle.FULL, español) + "%20tiene%20cita%20a%20las%20" + cita.getHoraInicio() + ".%20Le%20rogamos%20que%20confirme%20su%20cita.%20¡Muchas%20gracias!");
        try {
            java.awt.Desktop.getDesktop().browse(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
