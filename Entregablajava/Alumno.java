package Entregablajava;

import java.util.ArrayDeque;
import java.util.Deque;

enum Programa_Academico{
    CURSO,
    BOOTCAMP,
    DIPLOMADO;
}

public abstract class Alumno extends Persona {
    Deque<Double> Lista_notas = new ArrayDeque<>();
    Double promedio = 0.0;
    boolean aprobado = false;

    public Alumno(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }

    abstract boolean Aprobacion();

    void Calculo_promedio(){
        for (Double nota : Lista_notas){
            promedio += nota;
        }
        promedio/=Lista_notas.size();
    }

    void Registrar_nota(Double nota){
        Lista_notas.push(nota);
        System.out.println("Nota registrada con exito");
    }

    void Deshacer_nota(Double nota){
        Lista_notas.pop();
        System.out.println("Nota eliminada con exito");
    }
}    

class AlumnoCurso extends Alumno{
    Programa_Academico programa = Programa_Academico.CURSO;

    public AlumnoCurso(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }

    @Override
    boolean Aprobacion(){
        if(promedio >= 10){
            aprobado = true;
        }
        return aprobado;
    }
}

class AlumnoDiplomado extends Alumno{
    Programa_Academico programa = Programa_Academico.DIPLOMADO;

    public AlumnoDiplomado(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }

    @Override
    boolean Aprobacion(){
        if(promedio >= 14){
            aprobado = true;
        }
        return aprobado;
    }
}

class AlumnoBootcamp extends Alumno{
    Programa_Academico programa = Programa_Academico.BOOTCAMP;

    public AlumnoBootcamp(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }

    @Override
    boolean Aprobacion(){
        for(Double nota : Lista_notas){
            if(nota<=14){
                aprobado = false;
            }else{
                aprobado = true;
            }
        }
        return aprobado;
    }
}