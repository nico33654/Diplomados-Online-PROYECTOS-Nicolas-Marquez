#include <iostream>
#include <string>

using namespace std;

#ifndef PERSONA_CPP
#define PERSONA_CPP
class Persona{
    protected:
        string cedula;
        string nombre;
        string correo;
        
    public:

        Persona(string cedula, string nombre, string correo){
            setCedula(cedula);
            setNombre(nombre);
            setCorreo(correo);
        }
        virtual ~Persona() {} 
        void setCedula(string cdla){
            this->cedula = cdla;
        }

        void setNombre(string nombre){
            this->nombre = nombre;
        }

        void setCorreo(string correo){
            this->correo = correo;
        }

        string getCedula(){
            return cedula;
        }

        string getNombre(){
            return nombre;
        }

        string getCorreo(){
            return correo;
        }
};

#endif