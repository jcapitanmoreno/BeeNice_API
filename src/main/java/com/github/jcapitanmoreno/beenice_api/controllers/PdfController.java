package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.services.PdfService;
import com.github.jcapitanmoreno.beenice_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PdfService pdfService;

    /**
     * Exporta los datos de un usuario específico en formato PDF.
     * @param usuarioId ID del usuario cuyos datos se desean exportar.
     * @return Archivo PDF con los datos del usuario.
     * @throws Exception Si ocurre un error durante la generación del PDF.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<byte[]> exportUsuarioPdf(@PathVariable Long usuarioId) {
        try {
            List<Object[]> usuarioData = usuarioRepository.getUsuarioDataForPdf(usuarioId);
            byte[] pdfBytes = pdfService.generateUsuarioPdf(usuarioData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "usuario_reporte.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}