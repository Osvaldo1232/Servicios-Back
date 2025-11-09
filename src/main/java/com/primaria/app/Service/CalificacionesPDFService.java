package com.primaria.app.Service;

import com.primaria.app.DTO.*;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
        Document document = new Document(PageSize.A4.rotate()); // mejor en horizontal si hay muchas materias
        PdfWriter.getInstance(document, baos);
        document.open();

        Font tituloCiclo = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font subTitulo = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font textoNormal = new Font(Font.HELVETICA, 11);
        
        // ======== Encabezado con logo y nombre de la escuela ========
        InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logo.png");
        Image logo = null;
        if (logoStream != null) {
            logo = Image.getInstance(javax.imageio.ImageIO.read(logoStream), null);
            logo.scaleToFit(80, 80); // tamaño ajustable
        }

        PdfPTable encabezado = new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        encabezado.setWidths(new float[]{1, 4}); // 1 parte logo, 4 partes nombre

        PdfPCell cellLogo = new PdfPCell();
        cellLogo.setBorder(Rectangle.NO_BORDER);
        if (logo != null) {
            cellLogo.addElement(logo);
        }
        encabezado.addCell(cellLogo);

        PdfPCell cellNombre = new PdfPCell(new Phrase("Escuela Primaria José María", new Font(Font.HELVETICA, 16, Font.BOLD)));
        cellNombre.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellNombre.setBorder(Rectangle.NO_BORDER);
        encabezado.addCell(cellNombre);

        document.add(encabezado);
        document.add(Chunk.NEWLINE);

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

                // Tabla: Materia | Trimestre 1 | Trimestre 2 | Trimestre 3 | Promedio
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{4, 2, 2, 2, 2});

                // Header
                table.addCell(new PdfPCell(new Phrase("Materia", subTitulo)));
                table.addCell(new PdfPCell(new Phrase("Trimestre 1", subTitulo)));
                table.addCell(new PdfPCell(new Phrase("Trimestre 2", subTitulo)));
                table.addCell(new PdfPCell(new Phrase("Trimestre 3", subTitulo)));
                table.addCell(new PdfPCell(new Phrase("Promedio Final", subTitulo)));

                for (MateriaCalificacionPDFDTO materia : gradoDTO.getMaterias()) {
                    table.addCell(new PdfPCell(new Phrase(materia.getNombreMateria(), textoNormal)));
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(materia.getTrimestre1()), textoNormal)));
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(materia.getTrimestre2()), textoNormal)));
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(materia.getTrimestre3()), textoNormal)));
                    table.addCell(new PdfPCell(new Phrase(String.format("%.2f", materia.getCalificacionFinal()), textoNormal)));

                }

                document.add(table);

                Paragraph pPromedioGrado = new Paragraph(
                        "Promedio del grado: " + String.format("%.2f", gradoDTO.getPromedioGrado()), 
                        subTitulo
                );

                pPromedioGrado.setSpacingAfter(15f);
                document.add(pPromedioGrado);
            }

            document.add(new Paragraph("\n")); // salto de línea entre ciclos
        }

        document.close();
        return baos.toByteArray();
    }
}
