import tkinter as tk
from tkinter import ttk, messagebox
import random

class Equipo:
    def __init__(self, nombre, entrenador):
        self.nombre = nombre
        self.entrenador = entrenador
        self.jugadores = []

    def mostrar_info(self):
        info_equipo = f"Equipo: {self.nombre}, Entrenador: {self.entrenador}\n"
        info_jugadores = "\n".join([f"  - Jugador: {jugador.nombre}, Edad: {jugador.edad}, Posición: {jugador.posicion}" for jugador in self.jugadores])
        return info_equipo + info_jugadores

    def agregar_jugador(self, jugador):
        self.jugadores.append(jugador)

class Jugador:
    def __init__(self, nombre, edad, posicion):
        self.nombre = nombre
        self.edad = edad
        self.posicion = posicion

class Partido:
    def __init__(self, equipo_local, equipo_visitante, estadio, resultado):
        self.equipo_local = equipo_local
        self.equipo_visitante = equipo_visitante
        self.estadio = estadio
        self.resultado = resultado

    def mostrar_resultado(self):
        return f"{self.equipo_local.nombre} vs {self.equipo_visitante.nombre}: {self.resultado} - Estadio: {self.estadio.nombre}, Ciudad: {self.estadio.ciudad}"

class Grupo:
    def __init__(self, nombre):
        self.nombre = nombre
        self.equipos = []

    def mostrar_info(self):
        info_grupo = f"Grupo: {self.nombre}\n"
        info_equipos = "\n".join([f"  - Equipo: {equipo.nombre}" for equipo in self.equipos])
        return info_grupo + info_equipos

    def agregar_equipo(self, equipo):
        self.equipos.append(equipo)

class Estadio:
    def __init__(self, nombre, ciudad, capacidad):
        self.nombre = nombre
        self.ciudad = ciudad
        self.capacidad = capacidad

class Mundial:
    def __init__(self):
        self.grupos = []
        self.estadios = []

    def registrar_grupo(self, grupo):
        self.grupos.append(grupo)

    def registrar_estadio(self, estadio):
        self.estadios.append(estadio)

    def obtener_equipo_por_nombre(self, nombre_equipo):
        for grupo in self.grupos:
            for equipo in grupo.equipos:
                if equipo.nombre == nombre_equipo:
                    return equipo
        return None

    def obtener_estadios(self):
        return self.estadios

    def generar_equipos_aleatorios(self):
        nombres_equipos = [
            "Argentina", "Brasil", "Chile", "Uruguay",
            "Francia", "Alemania", "España", "Portugal",
            "Inglaterra", "Italia", "Holanda", "Bélgica",
            "Croacia", "Serbia", "México", "Estados Unidos"
        ]

        nombres_entrenadores = [
            "Lionel Scaloni", "Tite", "Reinaldo Rueda", "Óscar Tabárez",
            "Didier Deschamps", "Joachim Löw", "Luis Enrique", "Fernando Santos",
            "Gareth Southgate", "Roberto Mancini", "Frank de Boer", "Roberto Martínez",
            "Zlatko Dalić", "Dragan Stojković", "Gerardo Martino", "Gregg Berhalter"
        ]

        posiciones = ["Portero", "Defensa", "Centrocampista", "Delantero"]

        nombres_jugadores = [
            "Lionel Messi", "Cristiano Ronaldo", "Neymar", "Kylian Mbappé",
            "Kevin De Bruyne", "Harry Kane", "Robert Lewandowski", "Erling Haaland",
            "Mohamed Salah", "Virgil van Dijk", "Bruno Fernandes", "Joshua Kimmich",
            "Trent Alexander-Arnold", "Rúben Dias", "Jadon Sancho", "Phil Foden",
            "Jude Bellingham", "Pedri", "Gavi", "Ansu Fati", "Alphonso Davies",
            "Kai Havertz", "Mason Mount", "Timo Werner", "Federico Valverde",
            "João Félix", "Matthijs de Ligt", "Frenkie de Jong", "Lautaro Martínez",
            "Federico Chiesa", "Nicolò Barella", "Sandro Tonali", "Dušan Vlahović"
        ]

        equipos = []
        for nombre_equipo in nombres_equipos:
            entrenador = random.choice(nombres_entrenadores)
            equipo = Equipo(nombre_equipo, entrenador)
            for _ in range(random.randint(18, 23)):
                nombre_jugador = random.choice(nombres_jugadores)
                edad_jugador = random.randint(18, 38)
                posicion_jugador = random.choice(posiciones)
                jugador = Jugador(nombre_jugador, edad_jugador, posicion_jugador)
                equipo.agregar_jugador(jugador)
            equipos.append(equipo)

        random.shuffle(equipos)

        grupos = []
        for i in range(0, len(equipos), 4):
            grupo = Grupo(f"Grupo {chr(65 + len(grupos))}")
            for j in range(i, i + 4):
                if j < len(equipos):
                    grupo.agregar_equipo(equipos[j])
            grupos.append(grupo)

        self.grupos = grupos

    def generar_estadios(self):
        nombres_estadios = [
            ("Maracaná", "Río de Janeiro", 78000),
            ("Wembley", "Londres", 90000),
            ("Camp Nou", "Barcelona", 99000),
            ("Santiago Bernabéu", "Madrid", 81000),
            ("San Siro", "Milán", 80018),
            ("Estadio Azteca", "Ciudad de México", 87000),
            ("Allianz Arena", "Múnich", 75000),
            ("Old Trafford", "Manchester", 75643)
        ]

        for nombre, ciudad, capacidad in nombres_estadios:
            estadio = Estadio(nombre, ciudad, capacidad)
            self.estadios.append(estadio)

    def mostrar_grupos(self):
        info_grupos = ""
        for grupo in self.grupos:
            info_grupos += grupo.mostrar_info() + "\n\n"
        return info_grupos

class MundialApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Mundial")
        self.root.configure(bg='black')  

        self.mundial = Mundial()
        
        self.mundial.generar_equipos_aleatorios()
        self.mundial.generar_estadios()
        
        self.equipo_seleccionado_var = tk.StringVar()
      
        self.label_mostrar_grupos = tk.Label(root, text="Grupos del Mundial", bg='black', fg='white')
        self.label_mostrar_grupos.pack()

        self.text_mostrar_grupos = tk.Text(root, height=20, width=100)
        self.text_mostrar_grupos.pack()
        
        self.actualizar_mostrar_grupos()

        self.label_elegir_equipo = tk.Label(root, text="Elegir Equipo:", bg='black', fg='white')
        self.label_elegir_equipo.pack()

        self.combo_elegir_equipo = ttk.Combobox(root, textvariable=self.equipo_seleccionado_var, state="readonly", width=50)
        self.combo_elegir_equipo.pack()

        self.boton_mostrar_info = tk.Button(root, text="Mostrar Información del Equipo", command=self.mostrar_info)
        self.boton_mostrar_info.pack()

        self.label_info_jugadores = tk.Label(root, text="Información de Jugadores y Partidos", bg='black', fg='white')
        self.label_info_jugadores.pack()

        self.text_info_jugadores = tk.Text(root, height=20, width=100)
        self.text_info_jugadores.pack()
       
        self.cargar_combobox_equipos()

    def actualizar_mostrar_grupos(self):
        self.text_mostrar_grupos.delete(1.0, tk.END)
        self.text_mostrar_grupos.insert(tk.END, self.mundial.mostrar_grupos())

    def cargar_combobox_equipos(self):
        equipos = [equipo.nombre for grupo in self.mundial.grupos for equipo in grupo.equipos]
        self.combo_elegir_equipo['values'] = equipos

    def mostrar_info(self):
        equipo_seleccionado = self.equipo_seleccionado_var.get()

        if not equipo_seleccionado:
            messagebox.showwarning("Error", "Por favor, seleccione un equipo.")
            return

        equipo = self.mundial.obtener_equipo_por_nombre(equipo_seleccionado)

        if not equipo:
            messagebox.showwarning("Error", "No se encontró el equipo seleccionado.")
            return

        resultados = []
        for grupo in self.mundial.grupos:
            for otro_equipo in grupo.equipos:
                if otro_equipo.nombre != equipo_seleccionado:
                    estadio = random.choice(self.mundial.obtener_estadios())
                    resultado_partido = random.choice(["2-1", "1-3", "0-0", "3-2"]) 
                    partido = Partido(equipo, otro_equipo, estadio, resultado_partido)
                    resultados.append(partido.mostrar_resultado())

        self.text_info_jugadores.delete(1.0, tk.END)
        self.text_info_jugadores.insert(tk.END, equipo.mostrar_info() + "\n\nResultados de Partidos:\n")
        for resultado in resultados:
            self.text_info_jugadores.insert(tk.END, resultado + "\n")

root = tk.Tk()
app = MundialApp(root)
root.mainloop()