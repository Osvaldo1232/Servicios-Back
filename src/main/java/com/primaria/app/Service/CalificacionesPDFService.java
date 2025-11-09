package com.primaria.app.Service;

import com.primaria.app.DTO.*;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class CalificacionesPDFService {

    private final CalificacionService calificacionesService;

    public CalificacionesPDFService(CalificacionService calificacionesService) {
        this.calificacionesService = calificacionesService;
    }

    public byte[] generarPDFAlumno(String idAlumno) throws Exception {
        List<CicloCalificacionDTO> ciclos = calificacionesService.obtenerCalificacionesPorAlumno(idAlumno);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font tituloCiclo = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font subTitulo = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font textoNormal = new Font(Font.HELVETICA, 11);

        for (CicloCalificacionDTO ciclo : ciclos) {
            // Título ciclo escolar
            Paragraph pCiclo = new Paragraph("CICLO ESCOLAR: " + ciclo.getCicloEscolar(), tituloCiclo);
            pCiclo.setSpacingAfter(10f);
            document.add(pCiclo);

            for (GradoCalificacionDTO gradoDTO : ciclo.getGrados()) {
                Paragraph pGrado = new Paragraph("Grado: " + gradoDTO.getGradoNombre(), subTitulo);
                document.add(pGrado);
                Paragraph pAlumno = new Paragraph("Alumno: " + gradoDTO.getNombreAlumno(), textoNormal);
                pAlumno.setSpacingAfter(5f);
                document.add(pAlumno);

                // Tabla
                PdfPTable table = new PdfPTable(5); // Materia, Trimestre, Calificación, Promedio Final, espacio
                table.setWidthPercentage(100);
                table.setWidths(new float[]{4, 2, 2, 2, 1});

                // Header
                table.addCell("Materia");
                table.addCell("Trimestre");
                table.addCell("Calificación");
                table.addCell("Promedio Final");
                table.addCell(" "); // Espacio para estética

                for (MateriaCalificacionPDFDTO materia : gradoDTO.getMaterias()) {
                    for (TrimestreCalificacionDTO trimestre : materia.getTrimestres()) {
                        table.addCell(new PdfPCell(new Phrase(materia.getNombreMateria(), textoNormal)));
                        table.addCell(new PdfPCell(new Phrase(trimestre.getNombre(), textoNormal)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(trimestre.getPromedio()), textoNormal)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(materia.getCalificacionFinal()), textoNormal)));
                        table.addCell(""); // espacio
                    }
                }

                document.add(table);

                Paragraph pPromedioGrado = new Paragraph("Promedio del grado: " + gradoDTO.getPromedioGrado(), subTitulo);
                pPromedioGrado.setSpacingAfter(15f);
                document.add(pPromedioGrado);
            }

            document.add(new Paragraph("\n")); // salto de línea entre ciclos
        }

        document.close();
        return baos.toByteArray();
    }
}
