package Entregablejava;
import java.util.ArrayList;
import java.util.Stack;

enum Programa_Academico{
    CURSO,
    BOOTCAMP,
    DIPLOMADO;
}

public abstract class Alumno extends Persona {
    static ArrayList<String> Aprobados = new ArrayList<>();
    Stack<Double> Lista_notas = new Stack<>();
    Double promedio = 0.0;
    boolean aprobado = false;
    Programa_Academico programa;

    public Alumno(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }

    abstract boolean Aprobacion();

    void Calculo_promedio(){
        promedio = 0.0;
        for (Double nota : Lista_notas){
            promedio += nota;
        }
        promedio/=Lista_notas.size();
    }

    void Registrar_nota(Double nota){
        Lista_notas.push(nota);
        System.out.println("Nota registrada con exito");
    }

    void Deshacer_nota(){
        Lista_notas.pop();
        System.out.println("Nota eliminada con exito");
    }
    @Override
    public String toString(){
        String mensaje = "\n--> " + cedula + "-- "+ nombre;
        return mensaje;
    }
}    

class AlumnoCurso extends Alumno{
    

    public AlumnoCurso(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
        this.programa = Programa_Academico.CURSO;
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

    public AlumnoDiplomado(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
        this.programa = Programa_Academico.DIPLOMADO;
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
    

    public AlumnoBootcamp(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
        this.programa = Programa_Academico.BOOTCAMP;
    }

    @Override
    boolean Aprobacion(){
        for(Double nota : Lista_notas){
            if(nota>=14 && Lista_notas.size() == 3){
                aprobado = true;
            }else{
                aprobado = false;
            }
        }
        return aprobado;
    }
}