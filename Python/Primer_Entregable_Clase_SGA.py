from enum import Enum
from abc import ABC
from collections import deque
import os

class Programa_Academico(Enum):
    Curso = 1
    Diplomado = 2
    Bootcamp = 3


class Persona:
    def __init__(self, ID, nombre, correo):
        self.cedula : str = ID
        self.nombre : str = nombre
        self.correo : str = correo

class Alumno(Persona,ABC):


    def __init__(self, ID, nombre, correo, lista_notas = None):
        super().__init__(ID, nombre, correo)
        self.lista_notas = deque()
        if lista_notas is not None:
            if isinstance(lista_notas, list):
                self.lista_notas.extend(lista_notas)
            else:
                self.lista_notas.append(lista_notas)
        self.programa = None
        self.promedio : float = 0.0
        self.aprobado : bool = False

    def Aprobacion(self) -> bool:
        pass
        
    def Calculo_promedio(self) -> float:
        self.promedio = float(sum(self.lista_notas)/3)

    def Registrar_nota(self, nota : float):
            self.lista_notas.append(nota)
            os.system('cls')
            print('Nota registrada con exito')



    def Deshacer_nota(self):
        if len(self.lista_notas) == 0:
            print('No se puede deshacer la nota puesto que no hay nota registrada \n')
            return

        self.lista_notas.pop()
        os.system('cls')
        print('Nota eliminada con exito')
        

class AlumnoCurso(Alumno):
    def __init__(self, ID, nombre, correo, lista_notas=None):
        super().__init__(ID, nombre, correo, lista_notas)
        self.programa = Programa_Academico.Curso

    def Aprobacion(self) -> bool:
        self.Calculo_promedio()
        if self.promedio >= 10:
            self.aprobado = True
            return self.aprobado
        else: return self.aprobado

class AlumnoDiplomado(Alumno):
    def __init__(self, ID, nombre, correo, lista_notas=None):
        super().__init__(ID, nombre, correo, lista_notas)
        self.programa = Programa_Academico.Diplomado

    def Aprobacion(self) -> bool:
        self.Calculo_promedio()
        if self.promedio >= 14:
            self.aprobado = True
            return self.aprobado
        else: return self.aprobado

class AlumnoBootcamp(Alumno):
    def __init__(self, ID, nombre, correo, lista_notas=None):
        super().__init__(ID, nombre, correo, lista_notas)
        self.programa = Programa_Academico.Bootcamp

    def Aprobacion(self) -> bool:
        self.Calculo_promedio()
        for i in self.lista_notas:
            if i < 14:
                self.aprobado = False
                break
            else:
                self.aprobado = True
        return self.aprobado
        

class Profesor(Persona):
    def __init__(self, ID, nombre, correo, Especialidad, Materia):
        super().__init__(ID, nombre, correo)
        self.Especilidad_academica : str = Especialidad
        self.Materia_asignada : str = Materia

    def __str__(self):
        return "Soy un profesor " , self.cedula


class GestorArchivos:
    def __init__(self):
        self.listaAlumnos : list[Alumno] = []
        self.listaProfesores : list[Profesor] = []
        self.Aprobados : list[Alumno] = []

    def aniadirAlumno(self , objeto : Alumno):
        linea : str = ''
        with open("Alumnos.txt" , "a") as archivo:
            linea = f"{objeto.cedula},{objeto.nombre},{objeto.correo},{objeto.programa.name}"
            archivo.write(linea + "\n")

    def actualizarAlumnostxt(self, objeto : Alumno , posicion : int):
        self.listaAlumnos[posicion] = objeto
        with open("Alumnos.txt" , "w") as archivo:
            pass

        linea : str = ''
        for user in self.listaAlumnos:
            linea = f"{user.cedula},{user.nombre},{user.correo},{user.programa.name}"

            for nota in user.lista_notas:
                linea += "," + str(nota)

            with open("Alumnos.txt" , "a") as archivo:
                archivo.write(linea + "\n")
    
    def aniadirProfesorestxt(objeto : Profesor):
        linea : str = ''
        linea = f"{objeto.cedula},{objeto.nombre},{objeto.correo},{objeto.Materia_asignada},{objeto.Especilidad_academica}"

        with open("Profesores.txt" , "a") as archivo:
            archivo.write(linea + "\n")

    def recibirAlumnostxt(self):
        with open("Alumnos.txt" , "r") as archivo:
            lineas = archivo.readlines()

            for line in lineas:
                lineaSeparada = []
                lineaSeparada = line.strip().split(",")
                cedula = lineaSeparada[0].strip()
                nombre = lineaSeparada[1].strip()
                correo = lineaSeparada[2].strip()
                programa  = lineaSeparada[3].strip().upper()
                notas = []

                if(len(lineaSeparada) > 4):
                    for i in range(4 , len(lineaSeparada)):
                        notas.append(float(lineaSeparada[i]))

                match(programa):
                    case "CURSO":
                        user = AlumnoCurso(cedula,nombre,correo,notas)
                        self.listaAlumnos.append(user)

                    case "DIPLOMADO":
                        user = AlumnoDiplomado(cedula,nombre,correo,notas)
                        self.listaAlumnos.append(user)

                    case _:
                        user = AlumnoBootcamp(cedula,nombre,correo,notas)
                        self.listaAlumnos.append(user)

    def recibirProfesorestxt(self):
            with open("Profesores.txt" , "r") as archivo:
                lineas = archivo.readlines()
                for line in lineas:
                    lineaSeparada = []
                    lineaSeparada = line.split(",")

                    user = Profesor(lineaSeparada[0],lineaSeparada[1],lineaSeparada[2],lineaSeparada[3],lineaSeparada[4])
                    self.listaProfesores.append(user)

    def crearCertificadostxt(self):
        for user in self.listaAlumnos:
            if(user.Aprobacion()):
                self.Aprobados.append(user)

        if len(self.listaAlumnos) > 0:
            for i in range(len(self.Aprobados)):
                line : str = ''
                line = f'{i + 1}. [{self.listaAlumnos[i].cedula}] {self.listaAlumnos[i].nombre} \n  -PROGRAMA : {self.listaAlumnos[i].programa.name.lower()}\n  -PROMEDIO FINAL : {round(self.listaAlumnos[i].promedio, 2)}\n\n\n'

                with open("Certificados.txt" , "a") as archivo:
                    archivo.write(line)

        else:
            print("Ningun alumno ha aprobado")