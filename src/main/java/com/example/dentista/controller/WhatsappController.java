package com.example.dentista.controller;

import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;

import java.net.MalformedURLException;
import java.net.URL;

public class WhatsappController {

    public static void whatsapp(Cliente cliente, Cita cita) throws MalformedURLException {
        URL url = new URL("https://wa.me/" + "+34" + cliente.getTelefono() + "/?" + "text=¡Buenas!%20Le%20recordamos%20que%20el%20día%20" +
                cita.getFechaCita() + "%20tiene%20cita%20a%20las%20" + cita.getHoraInicio() + ".%20Le%20rogamos%20que%20confirme%20su%20cita.%20¡Muchas%20gracias!");
        try {
            java.awt.Desktop.getDesktop().browse(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
