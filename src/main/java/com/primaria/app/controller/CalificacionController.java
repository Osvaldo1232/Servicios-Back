package com.primaria.app.controller;
import org.springframework.http.MediaType;
import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.DTO.CicloCalificacionDTO;
import com.primaria.app.DTO.MensajeDTO;
import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Service.CalificacionService;
import com.primaria.app.Service.ConsultaCalificacionService;
import com.primaria.app.Service.CalificacionesPDFService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.primaria.app.DTO.AlumnoCalificacionesDTO;
import com.primaria.app.DTO.CalificacionAlumnoCicloDTO;
import com.primaria.app.DTO.CalificacionAlumnoProjection;
import com.primaria.app.DTO.FiltroCalificacionesDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.DTO.MateriaTrimestresDTO;



import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/calificaciones")
@Tag(name = "Calificaciones", description = "API para asignar y consultar calificaciones")
public class CalificacionController {

	
	private final ConsultaCalificacionService calificacionServiceC;

	 private final CalificacionesPDFService pdfService;
	 
    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionesPDFService calificacionesPDFService,CalificacionService calificacionService, ConsultaCalificacionService calificacionServiceC) {
        this.calificacionService = calificacionService;
        this.calificacionServiceC = calificacionServiceC;
        this.pdfService=calificacionesPDFService;
    }

    // -------------------------------
    // Asignar calificación
    // -------------------------------
    @PostMapping("/asignar")
    @Operation(
        summary = "RF2.4 y RF2.6 Asignar calificación",
        description = "Asigna una calificación a un alumno en una materia y trimestre específico"
    )
    public ResponseEntity<MensajeDTO> asignarCalificacion(@Valid @RequestBody CalificacionFinalDTO dto) {
        try {
            calificacionService.asignarCalificacion(dto);
            return ResponseEntity.ok(new MensajeDTO("Registro exitoso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeDTO(e.getMessage()));
        }
    }

    // -------------------------------
    // Obtener calificación por ID
    // -------------------------------
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener calificación por ID",
        description = "Obtiene una calificación por su ID"
    )
    public ResponseEntity<Calificacion_final> obtenerCalificacion(@PathVariable String id) {
        return calificacionService
                .buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------
    // Listar calificaciones por grupo
    // -------------------------------
    @PostMapping("/grupo")
    @Operation(
        summary = "Obtener calificación por ID",
        description = "Filtros para obtener las calificaciones (ciclo, grado, grupo)"
    )
    public ResponseEntity<List<AlumnoCalificacionesDTO>> listarPorGrupo(@RequestBody FiltroCalificacionesDTO filtro) {
        List<AlumnoCalificacionesDTO> lista = calificacionService.listarCalificacionesPorGrupo(filtro);
        return ResponseEntity.ok(lista);
    }

    // -------------------------------
    // Obtener calificaciones por alumno y ciclo
    // -------------------------------
    @GetMapping("/alumno/{alumnoId}/ciclo/{cicloId}")
    @Operation(
        summary = "RF3.1 Obtener calificaciones por alumno y ciclo",
        description = "Devuelve las calificaciones agrupadas por materia y ordenadas por trimestre"
    )
    public List<MateriaTrimestresDTO> obtenerCalificaciones(
            @Parameter(description = "ID del alumno") @PathVariable String alumnoId,
            @Parameter(description = "ID del ciclo") @PathVariable String cicloId) {
        return calificacionService.obtenerCalificacionesPorAlumnoYCiclo(alumnoId, cicloId);
    }
    
    @Operation(
            summary = "Generar reporte PDF de calificaciones",
            description = "Genera y descarga un archivo PDF con las calificaciones de un alumno en un ciclo escolar determinado."
    )
  
    
    @GetMapping("/pdf/alumno/{alumnoId}/ciclo/{cicloId}")
    public void exportarCalificacionesPDF(
            @PathVariable String alumnoId,
            @PathVariable String cicloId,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=calificaciones.pdf");

        List<MateriaCalificacionResDTO> calificaciones =
        		calificacionService.obtenerCalificacionesPorAlumnoYciclo(alumnoId, cicloId);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Logo (ajusta la ruta según tu proyecto)
        String imagePath = "src/main/resources/logo.png";
        Image img = Image.getInstance(imagePath);
        img.scaleToFit(50, 50);

        // Encabezado con logo + título
        Paragraph titulo = new Paragraph("Reporte de Calificaciones",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));

        PdfPTable encabezado = new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        encabezado.setWidths(new float[]{1f, 5f});

        PdfPCell imgCell = new PdfPCell(img, false);
        imgCell.setBorder(PdfPCell.NO_BORDER);
        PdfPCell titleCell = new PdfPCell(titulo);
        titleCell.setBorder(PdfPCell.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        encabezado.addCell(imgCell);
        encabezado.addCell(titleCell);
        document.add(encabezado);

        document.add(new Paragraph(" ")); // Espacio

        // Datos del alumno y ciclo
        if (!calificaciones.isEmpty()) {
            String nombreGrado = calificaciones.get(0).getNombreGrado();
            Paragraph info = new Paragraph(
                    "Alumno ID: " + alumnoId +
                            " | Ciclo ID: " + cicloId +
                            " | Grado: " + nombreGrado,
                    FontFactory.getFont(FontFactory.HELVETICA, 11)
            );
            document.add(info);
            document.add(new Paragraph(" "));
        }

        // Tabla
        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3f, 5f, 2f});

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Stream.of("ID Materia", "Materia", "Calificación").forEach(col -> {
            PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new Color(220, 220, 220));
            tabla.addCell(cell);
        });

        for (MateriaCalificacionResDTO m : calificaciones) {
            tabla.addCell(m.getIdMateria());
            tabla.addCell(m.getNombreMateria());
            tabla.addCell(String.valueOf(m.getCalificacionActual()));
        }

        document.add(tabla);
        document.close();
    }
    
    @Operation(
            summary = "RF2.5 Listar calificaciones por grado",
            description = "Devuelve las calificaciones de todos los alumnos del grado especificado, incluyendo las calificaciones por trimestre y el promedio final."
        )
        @GetMapping("/grado/{gradoId}")
        public List<CalificacionAlumnoProjection> listarPorGrado(
            @Parameter(description = "UUID del grado a consultar", required = true)
            @PathVariable String gradoId
        ) {
            return calificacionServiceC.obtenerPorGrado(gradoId);
        }
    
    @GetMapping("/grados/{gradoId}")
    @Operation(
        summary = "RF2.5 Obtener calificaciones por grado",
        description = "Devuelve las calificaciones de todos los alumnos del grado, incluyendo las calificaciones por trimestre y el promedio final"
    )
    public List<CalificacionAlumnoCicloDTO> obtenerCalificacionesPorGrado(
            @Parameter(description = "UUID del grado a consultar", required = true)
            @PathVariable String gradoId
    ) {
        return calificacionService.obtenerPorGrado(gradoId);
    }
    
    
    @GetMapping("/alumno/{idAlumno}")
    @Operation(summary = "Obtiene calificaciones por alumno",
               description = "Devuelve las calificaciones de todas las materias por trimestre y el promedio final, agrupadas por ciclo y grado")
    public ResponseEntity<List<CicloCalificacionDTO>> obtenerCalificacionesPorAlumno(
            @PathVariable String idAlumno) {
        try {
            List<CicloCalificacionDTO> calificaciones = calificacionService.obtenerCalificacionesPorAlumno(idAlumno);
            return ResponseEntity.ok(calificaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
   @GetMapping(value = "/alumno/{idAlumno}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Genera un PDF con las calificaciones del alumno")
    public ResponseEntity<byte[]> generarPDF(@PathVariable String idAlumno) {
        try {
            byte[] pdfBytes = pdfService.generarPDFAlumno(idAlumno);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "calificaciones_alumno.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(("Error al generar PDF: " + e.getMessage()).getBytes());
        }
    }

    
    
    @GetMapping(value = "/alumno/{idAlumno}/pdfs", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Genera un PDF con las calificaciones del alumno")
    public ResponseEntity<byte[]> generarPDFs(@PathVariable String idAlumno) {
        try {
            byte[] pdfBytes = pdfService.generarPDFAlumno(idAlumno);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calificaciones_alumno.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error al generar PDF: " + e.getMessage()).getBytes());
        }
    }

}
