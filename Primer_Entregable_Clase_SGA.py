from enum import Enum
class Programa_Academico(Enum):
    Curso = 1
    Diplomado = 2
    Bootcamp = 3


class Persona:
    def __init__(self, ID, nombre, correo):
        self.cedula : str = ID
        self.nombre : str = nombre
        self.correo : str = correo

class Alumno(Persona):

    Aprobados = []

    def __init__(self, ID, nombre, correo, programa : str, lista_notas = None):
        super().__init__(ID, nombre, correo)

        if lista_notas is not None:
            self.lista_notas : list[float] = lista_notas

        else:
            self.lista_notas : list[float] = []

        self.promedio : float = 0.0
        self.aprobado : bool = False
        self.programa_academico = None

        if programa.lower() == 'curso':
            self.programa_academico = Programa_Academico.Curso
        elif programa.lower() == 'diplomado':
            self.programa_academico = Programa_Academico.Diplomado
        else:
            self.programa_academico = Programa_Academico.Bootcamp
        

    def Aprobacion(self) -> bool:
        if len(self.lista_notas) >= 3:
            self.Calculo_promedio()

            if self.programa_academico.value == 1 and self.promedio >=10:
                self.aprobado = True
                return True
            
            elif self.programa_academico.value == 2 and self.promedio >=14:
                self.aprobado = True
                return True

            elif self.programa_academico.value == 3:
                for i in self.lista_notas:
                    if i < 14:
                        self.aprobado = False
                        return False

                self.aprobado = True
                return True

        else:
            print(f'Faltan {3 - len(self.lista_notas)} notas')
            return False
        
    def Calculo_promedio(self) -> float:
        self.promedio = float(sum(self.lista_notas)/3)

    def Registrar_nota(self, nota : float):
        self.lista_notas.append(nota)
        print('Nota registrada con exito')

    def Deshacer_nota(self):
        if len(self.lista_notas) == 0:
            print('No se puede deshacer la nota puesto que no hay nota registrada \n')

        self.lista_notas.pop(len(self.lista_notas)-1)
        print('Nota eliminada con exito')
        

class Profesor(Persona):
    def __init__(self, ID, nombre, correo, Especialidad, Materia):
        super().__init__(ID, nombre, correo)
        self.Especilidad_academica : str = Especialidad
        self.Materia_asignada : str = Materia

