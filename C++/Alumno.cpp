#ifndef ALUMNO_CPP
#define ALUMNO_CPP

#include <iostream>
#include <string>
#include <sstream>
#include <stack>
#include <vector>
#include <fstream>
#include <cmath>
#include "Persona.cpp"
using namespace std;

enum class programaAcademico{
    CURSO,
    DIPLOMADO,
    BOOTCAMP
};

//formato de alumnos txt: cedula,nombre,correo,programa,notas


inline ostream& operator<<(ostream& os, const programaAcademico& prog) {
    switch (prog) {
        case programaAcademico::CURSO:     
            os << "CURSO"; break;
        case programaAcademico::DIPLOMADO: 
            os << "DIPLOMADO"; break;
        case programaAcademico::BOOTCAMP:  
            os << "BOOTCAMP"; break;
    }
    return os;
}

class Alumno : public Persona{
    public:
        
        programaAcademico programa;
        stack<double> listaNotas;
        double promedio;
        bool aprobado;
        
        Alumno(string cedula, string nombre, string correo) : Persona(cedula,nombre,correo){
            this->aprobado = false;
            this->promedio = 0.0;
        }

        Alumno(string cedula, string nombre, string correo, vector<double>Notas) : Persona(cedula,nombre,correo){
            this-> promedio = 0.0;
            this->aprobado = false;

            for(double nota: Notas){
                listaNotas.push(nota);
            }
        }

        virtual bool Aprobacion() = 0;
        
        double calculoPromedio(){
            if(listaNotas.empty()){
                promedio = 0.0;
                return promedio;
            }

            double notas = 0.0;
            stack<double> Copia = listaNotas;

            while(!Copia.empty()){
                notas += Copia.top();
                Copia.pop();
            }
            promedio = notas/listaNotas.size();
            return round(promedio);
        }

        void registrarNota(double nota){
            if((nota < 0 || nota > 20) || listaNotas.size() == 3){
                cout<<"Error: Nota invalida o Cantidad de notas maximas alcanzada\n\n";
                return;
            }else{
                listaNotas.push(nota);
                cout<<"Nota registrada con exito\n";
            }
        }

        void deshacerNota(){
            if(!listaNotas.empty()){
                listaNotas.pop();
                cout<<"Nota eliminada con exito\n\n";
            }else{
                cout<<"ERROR: sin Notas registradas\n\n";
            }
        }

    friend ostream& operator<<(ostream& os, const Alumno& al) {
        os <<al.nombre<<" --> "<<al.cedula<<"\n";
        return os;
    }

};


class AlumnoCurso : public Alumno{
    public:
        AlumnoCurso(string cedula, string nombre, string correo) : Alumno(cedula,nombre,correo){
            programa = programaAcademico::CURSO;
        }


        AlumnoCurso(string cedula, string nombre, string correo, vector<double> Notas) : Alumno(cedula,nombre,correo, Notas){
            programa = programaAcademico::CURSO;
        }

        bool Aprobacion() override{
            calculoPromedio();
            if(promedio >= 10 && listaNotas.size() == 3){
                aprobado = true;
            }
            return aprobado;
        }
};


class AlumnoDiplomado : public Alumno{
    public:
        AlumnoDiplomado(string cedula, string nombre, string correo, vector<double> Notas) : Alumno(cedula,nombre,correo, Notas){
            programa = programaAcademico::DIPLOMADO;
        }

        AlumnoDiplomado(string cedula, string nombre, string correo) : Alumno(cedula,nombre,correo){
            programa = programaAcademico::DIPLOMADO;
        }

        bool Aprobacion() override{
            calculoPromedio();
            if(promedio >= 14 && listaNotas.size() == 3){
                aprobado = true;
            }
            return aprobado;
        }

};


class AlumnoBootcamp : public Alumno{
    public:
        AlumnoBootcamp(string cedula, string nombre, string correo, vector<double> Notas) : Alumno(cedula,nombre,correo, Notas){
            programa = programaAcademico::BOOTCAMP;
        }

        AlumnoBootcamp(string cedula, string nombre, string correo) : Alumno(cedula,nombre,correo){
            programa = programaAcademico::BOOTCAMP;
        }

        bool Aprobacion() override{
            stack<double>Copia = listaNotas;
            while(!Copia.empty()){
                if(Copia.top() >= 14 && listaNotas.size() == 3 && !Copia.empty()){
                    aprobado = true;
                }else{
                    aprobado = false;
                    break;
                }
                Copia.pop();
            }
            return aprobado;
        }


};

#endif