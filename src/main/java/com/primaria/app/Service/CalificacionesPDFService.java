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
        List<CicloCalificacionDTO> ciclos = calificacionesService.obtenerCalificacionesPorAlumnos(idAlumno);
        ciclos.sort((a, b) -> {
            int anioA = Integer.parseInt(a.getCicloEscolar().split("-")[0]);
            int anioB = Integer.parseInt(b.getCicloEscolar().split("-")[0]);
            return Integer.compare(anioB, anioA); 
        });
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, baos);
        document.open();

        Font tituloCiclo = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font subTitulo = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font textoNormal = new Font(Font.HELVETICA, 11);
        
        // ======== Encabezado con logo y nombre de la escuela ========
        InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logoprimaria.png");
        Image logo = null;

        if (logoStream != null) {
            logo = Image.getInstance(javax.imageio.ImageIO.read(logoStream), null);
            logo.scaleToFit(80, 80);
        }

        PdfPTable encabezado = new PdfPTable(1);
        encabezado.setWidthPercentage(100);

        PdfPCell cellLogo = new PdfPCell();
        cellLogo.setBorder(Rectangle.NO_BORDER);
        cellLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
        if (logo != null) {
            cellLogo.addElement(logo);
        }
        encabezado.addCell(cellLogo);

        PdfPCell cellNombre = new PdfPCell(
                new Phrase("Escuela Primaria José María", new Font(Font.HELVETICA, 18, Font.BOLD))
        );
        cellNombre.setBorder(Rectangle.NO_BORDER);
        cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellNombre.setPaddingTop(5f);
        encabezado.addCell(cellNombre);

        document.add(encabezado);
        document.add(Chunk.NEWLINE);

        // ======= TÍTULO HISTORIAL ACADÉMICO CENTRADO =======
        Paragraph tituloGeneral = new Paragraph(
                "HISTORIAL ACADÉMICO",
                new Font(Font.HELVETICA, 16, Font.BOLD)
        );
        tituloGeneral.setAlignment(Element.ALIGN_CENTER);
        tituloGeneral.setSpacingAfter(15f);
        document.add(tituloGeneral);


        if (ciclos.isEmpty()) {
            // Si no hay ciclos válidos, mostrar mensaje
            Paragraph mensaje = new Paragraph(
                    "Para obtener el historial, debes haber presentado las materias de un grado.",
                    tituloCiclo
            );
            mensaje.setAlignment(Element.ALIGN_CENTER);
            mensaje.setSpacingBefore(100f); // espacio desde arriba
            document.add(mensaje);
        } else {
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
                    table.addCell(new PdfPCell(new Phrase(String.format("%.1f", materia.getCalificacionFinal()), textoNormal)));

                }

                document.add(table);

                Paragraph pPromedioGrado = new Paragraph(
                        "Promedio del " + gradoDTO.getGradoNombre() +": "+ String.format("%.1f", gradoDTO.getPromedioGrado()), 
                        subTitulo
                );

                pPromedioGrado.setSpacingAfter(15f);
                document.add(pPromedioGrado);
            }

            document.add(new Paragraph("\n")); // salto de línea entre ciclos
        }
        }

        document.close();
        return baos.toByteArray();
    }
}
