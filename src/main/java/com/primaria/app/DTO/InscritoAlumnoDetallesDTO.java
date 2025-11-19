package com.primaria.app.DTO;

public class InscritoAlumnoDetallesDTO {

    private String alumnoId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private String curp;

    private String gradoId;
    private String gradoNombre;

    private String grupoId;
    private String grupoNombre;

    private boolean esUltimaInscripcion;
    private boolean tieneTutor;

    // Información del tutor
    private String tutorNombreCompleto;
    private String tutorTelefono;
    private String tutorCorreo;
    private String parentesco;

    // 🔹 Constructor vacío
    public InscritoAlumnoDetallesDTO() {
    }

    // 🔹 Constructor completo
    public InscritoAlumnoDetallesDTO(
            String alumnoId,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String matricula,
            String curp,
            String gradoId,
            String gradoNombre,
            String grupoId,
            String grupoNombre,
            boolean esUltimaInscripcion,
            boolean tieneTutor,
            String tutorNombreCompleto,
            String tutorTelefono,
            String tutorCorreo,
            String parentesco
    ) {
        this.alumnoId = alumnoId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.curp = curp;
        this.gradoId = gradoId;
        this.gradoNombre = gradoNombre;
        this.grupoId = grupoId;
        this.grupoNombre = grupoNombre;
        this.esUltimaInscripcion = esUltimaInscripcion;
        this.tieneTutor = tieneTutor;
        this.tutorNombreCompleto = tutorNombreCompleto;
        this.tutorTelefono = tutorTelefono;
        this.tutorCorreo = tutorCorreo;
        this.parentesco = parentesco;
    }

    // 🔹 Getters y Setters

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getGradoId() {
        return gradoId;
    }

    public void setGradoId(String gradoId) {
        this.gradoId = gradoId;
    }

    public String getGradoNombre() {
        return gradoNombre;
    }

    public void setGradoNombre(String gradoNombre) {
        this.gradoNombre = gradoNombre;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public boolean isEsUltimaInscripcion() {
        return esUltimaInscripcion;
    }

    public void setEsUltimaInscripcion(boolean esUltimaInscripcion) {
        this.esUltimaInscripcion = esUltimaInscripcion;
    }

    public boolean isTieneTutor() {
        return tieneTutor;
    }

    public void setTieneTutor(boolean tieneTutor) {
        this.tieneTutor = tieneTutor;
    }

    public String getTutorNombreCompleto() {
        return tutorNombreCompleto;
    }

    public void setTutorNombreCompleto(String tutorNombreCompleto) {
        this.tutorNombreCompleto = tutorNombreCompleto;
    }

    public String getTutorTelefono() {
        return tutorTelefono;
    }

    public void setTutorTelefono(String tutorTelefono) {
        this.tutorTelefono = tutorTelefono;
    }

    public String getTutorCorreo() {
        return tutorCorreo;
    }

    public void setTutorCorreo(String tutorCorreo) {
        this.tutorCorreo = tutorCorreo;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
