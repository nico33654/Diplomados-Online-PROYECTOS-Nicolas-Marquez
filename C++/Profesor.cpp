#ifndef PROFESOR_CPP
#define PROFESOR_CPP
#include <iostream>
#include<string>
#include <fstream>
#include <vector>
#include "Persona.cpp"
using namespace std;

class Profesor : public Persona{
    public:
        string especialidadAcademica;
        string materiaAsignada;

        Profesor(string cedula, string nombre, string correo, string especialidadAcademica, string materiaAsignada) : Persona(cedula,nombre,correo){
            this->especialidadAcademica = especialidadAcademica;
            this->materiaAsignada = materiaAsignada;
        }


        friend ostream& operator<<(ostream& os, const Profesor& pr) {
            os <<pr.cedula<<" - "<<pr.nombre<< " - "<<pr.correo<<" - "<< pr.especialidadAcademica<<" - " <<pr.materiaAsignada<<"\n";
            return os;
        }
};

#endif