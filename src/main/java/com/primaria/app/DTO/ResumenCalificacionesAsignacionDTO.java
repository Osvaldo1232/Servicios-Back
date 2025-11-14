package com.primaria.app.DTO;

import java.util.List;

public class ResumenCalificacionesAsignacionDTO {

    private int totalAlumnos;
    private int alumnosAprobados;     // >= 6
    private int alumnosReprobados;    // < 6
    private List<PromedioAlumnoDTO> alumnos;

    public ResumenCalificacionesAsignacionDTO(int totalAlumnos, int alumnosAprobados, int alumnosReprobados, List<PromedioAlumnoDTO> alumnos) {
        this.totalAlumnos = totalAlumnos;
        this.alumnosAprobados = alumnosAprobados;
        this.alumnosReprobados = alumnosReprobados;
        this.alumnos = alumnos;
    }

    public int getTotalAlumnos() {
        return totalAlumnos;
    }

    public void setTotalAlumnos(int totalAlumnos) {
        this.totalAlumnos = totalAlumnos;
    }

    public int getAlumnosAprobados() {
        return alumnosAprobados;
    }

    public void setAlumnosAprobados(int alumnosAprobados) {
        this.alumnosAprobados = alumnosAprobados;
    }

    public int getAlumnosReprobados() {
        return alumnosReprobados;
    }

    public void setAlumnosReprobados(int alumnosReprobados) {
        this.alumnosReprobados = alumnosReprobados;
    }

    public List<PromedioAlumnoDTO> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<PromedioAlumnoDTO> alumnos) {
        this.alumnos = alumnos;
    }

}
