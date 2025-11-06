package com.primaria.app.DTO;

public interface CalificacionAlumnoProjection {
	String getId_alumno();
    String getNombre_alumno();
    String getNombre_materia();
    Double getTrimestre_1();   // para 'tri'
    Double getTrimestre_2();   // para 'trime 2'
    Double getTrimestre_3();   // para 'trime 3'
    Double getPromedio_final();
}
