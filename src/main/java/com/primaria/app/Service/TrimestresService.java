package com.primaria.app.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.primaria.app.DTO.TrimestresDTO;
import com.primaria.app.DTO.TrimestresResumenDTO;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.TrimestreRepository;







@Service
public class TrimestresService {

	 @Autowired
	    private TrimestreRepository trimestreRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<TrimestresDTO> listarTodos() {

	        // Orden deseado
	        List<String> orden = List.of("Trimestre 1", "Trimestre 2", "Trimestre 3");

	        return trimestreRepository.findAll()
	                .stream()
	                .sorted(Comparator.comparingInt(t ->
	                        orden.indexOf(t.getNombre())
	                ))
	                .map(t -> modelMapper.map(t, TrimestresDTO.class))
	                .toList();
	    }

	   
	    
	    public Optional<TrimestresDTO> obtenerPorUuid(String uuid) {
	        return trimestreRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, TrimestresDTO.class));
	    }


	    public TrimestresDTO guardar(TrimestresDTO dto) {
	    	Trimestres grupo = modelMapper.map(dto, Trimestres.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Trimestres guardado = trimestreRepository.save(grupo);
	        return modelMapper.map(guardado, TrimestresDTO.class);
	    }

	    
	    public Trimestres save(Trimestres materia) {
	        return trimestreRepository.save(materia);
	    }

	    
	    public boolean actualizar(String uuid, TrimestresDTO dto) {
	        Optional<Trimestres> existente = trimestreRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Trimestres ciclo = existente.get();
	          ciclo.setNombre(dto.getNombre());
	           ciclo.setEstatus(dto.getEstatus());
	           trimestreRepository.save(ciclo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Trimestres> existente = trimestreRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	trimestreRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<TrimestresResumenDTO> obtenerActivos() {
	        List<Trimestres> activos = trimestreRepository.findByEstatus(Estatus.ACTIVO);
	        return activos.stream()
	                .map(campo -> new TrimestresResumenDTO(campo.getId(), campo.getNombre()))
	                .collect(Collectors.toList());
	    }
	    
	    public byte[] generarPDF() throws IOException {
	        List<Trimestres> lista = trimestreRepository.findAll();

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        Document document = new Document(PageSize.A4, 36, 36, 60, 36);
	        PdfWriter.getInstance(document, baos);
	        document.open();

	        // 🔷 Logo (usa una imagen desde internet o desde resources/static/img/)
	     

	        // 🔷 Título
	        Paragraph titulo = new Paragraph("Lista de Trimestres", new Font(Font.HELVETICA, 18, Font.BOLD));
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        titulo.setSpacingAfter(20);
	        document.add(titulo);

	        // 🔷 Crear tabla
	        PdfPTable table = new PdfPTable(3); // 3 columnas
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10);
	        table.setWidths(new float[]{3f, 5f, 3f});

	        // Encabezados
	        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
	        PdfPCell cell;

	        cell = new PdfPCell(new Phrase("ID", headerFont));
	        cell.setBackgroundColor(new Color(0, 121, 182));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase("Nombre", headerFont));
	        cell.setBackgroundColor(new Color(0, 121, 182));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase("Estatus", headerFont));
	        cell.setBackgroundColor(new Color(0, 121, 182));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	        // Datos
	        Font dataFont = new Font(Font.HELVETICA, 11);
	        for (Trimestres t : lista) {
	            table.addCell(new Phrase(t.getId(), dataFont));
	            table.addCell(new Phrase(t.getNombre(), dataFont));
	            table.addCell(new Phrase(t.getEstatus() != null ? t.getEstatus().name() : "-", dataFont));
	        }

	        document.add(table);
	        document.close();

	        return baos.toByteArray();
	    }
	    
}
