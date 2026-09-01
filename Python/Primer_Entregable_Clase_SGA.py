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

    Aprobados = []

    def __init__(self, ID, nombre, correo, programa, lista_notas = None):
        super().__init__(ID, nombre, correo)
        self.lista_notas = deque()
        if lista_notas is not None:
            if isinstance(lista_notas, list):
                self.lista_notas.extend(lista_notas)
            else:
                self.lista_notas.append(lista_notas)

        self.promedio : float = 0.0
        self.aprobado : bool = False
        self.programa : Programa_Academico = None

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
    def __init__(self, ID, nombre, correo, programa : Programa_Academico, lista_notas=None):
        super().__init__(ID, nombre, correo, programa, lista_notas)
        self.programa_academico = programa

    def Aprobacion(self) -> bool:
        if self.promedio >= 10:
            self.aprobado = True
            return self.aprobado
        else: return self.aprobado

class AlumnoDiplomado(Alumno):
    def __init__(self, ID, nombre, correo, programa, lista_notas=None):
        super().__init__(ID, nombre, correo, programa, lista_notas)
        self.programa_academico = programa

    def Aprobacion(self) -> bool:
        if self.promedio >= 14:
            self.aprobado = True
            return self.aprobado
        else: return self.aprobado

class AlumnoBootcamp(Alumno):
    def __init__(self, ID, nombre, correo, programa, lista_notas=None):
        super().__init__(ID, nombre, correo, programa, lista_notas)
        self.programa_academico = programa

    def Aprobacion(self) -> bool:
        for i in self.lista_notas:
            if i < 14:
                self.aprobado = False
            else:
                self.aprobado = True
        return self.aprobado
        

class Profesor(Persona):
    def __init__(self, ID, nombre, correo, Especialidad, Materia):
        super().__init__(ID, nombre, correo)
        self.Especilidad_academica : str = Especialidad
        self.Materia_asignada : str = Materia

