package Entregablejava;

public class Profesor extends Persona{
    String Especialidad_academica;
    String Materia;
    
    public Profesor(String nombre, String cedula, String correo, String Especialidad_academico, String Materia) {
        super(nombre, cedula, correo);
        this.Especialidad_academica = Especialidad_academico;
        this.Materia = Materia;
    }
    
}
