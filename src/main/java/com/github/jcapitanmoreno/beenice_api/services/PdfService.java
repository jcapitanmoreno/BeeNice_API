package com.github.jcapitanmoreno.beenice_api.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateUsuarioPdf(List<Object[]> usuarioData) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Reporte de Usuario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        document.add(Chunk.NEWLINE);

        for (Object[] row : usuarioData) {
            document.add(new Paragraph("Usuario: " + row[0] + " (" + row[1] + ")"));
            document.add(new Paragraph("Grupo: " + row[2] + " - " + row[3]));
            document.add(new Paragraph("Nota: " + row[4]));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.addCell("Gasto");
            table.addCell("Total");
            table.addCell("Pagado");
            table.addCell(row[5] != null ? row[5].toString() : "N/A");
            table.addCell(row[6] != null ? row[6].toString() : "N/A");
            table.addCell(row[7] != null ? row[7].toString() : "N/A");

            document.add(table);
            document.add(Chunk.NEWLINE);

            if (row[8] != null) {
                document.add(new Paragraph("Pago: " + row[8]));
                document.add(new Paragraph("Total a Pagar: " + row[9]));
                document.add(new Paragraph("Pagado Hasta Ahora: " + row[10]));
                document.add(Chunk.NEWLINE);
            }
        }

        document.close();
        return out.toByteArray();
    }
}