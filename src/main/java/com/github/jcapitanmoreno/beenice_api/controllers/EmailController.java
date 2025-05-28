package com.github.jcapitanmoreno.beenice_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico con los detalles proporcionados.
     * @param nombre Nombre del remitente.
     * @param correo Correo electrónico del remitente.
     * @param mensaje Contenido del mensaje.
     * @return Respuesta indicando el estado del envío del correo.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String nombre, @RequestParam String correo, @RequestParam String mensaje) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(correo);
            mailMessage.setTo("jcapitanmoreno@gmail.com");
            mailMessage.setSubject("Mensaje de " + nombre);
            mailMessage.setText(mensaje);

            mailSender.send(mailMessage);
            return  ResponseEntity.ok("{\"status\": \"success\", \"message\": \"Correo enviado exitosamente\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"status\": \"error\", \"message\": \"Error al enviar el correo: " + e.getMessage() + "\"}");
        }
    }
}