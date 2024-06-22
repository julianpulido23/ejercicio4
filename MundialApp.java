package mundialapp;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class Equipo {
    private String nombre;
    private String entrenador;
    private List<Jugador> jugadores;
    private List<Partido> historialPartidos;

    public Equipo(String nombre, String entrenador) {
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.jugadores = new ArrayList<>();
        this.historialPartidos = new ArrayList<>();
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Equipo: ").append(nombre).append("\n");
        info.append("Entrenador: ").append(entrenador).append("\n\n");
        info.append("Jugadores:\n");
        for (Jugador jugador : jugadores) {
            info.append("  - ").append(jugador.toString()).append("\n");
        }
        return info.toString();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void agregarPartido(Partido partido) {
        historialPartidos.add(partido);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Partido> getHistorialPartidos() {
        return historialPartidos;
    }
}

class Jugador {
    private String nombre;
    private int edad;
    private String posicion;

    public Jugador(String nombre, int edad, String posicion) {
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return nombre + " (Edad: " + edad + ", Posición: " + posicion + ")";
    }
}

class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Estadio estadio;
    private String resultado;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, Estadio estadio, String resultado) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.estadio = estadio;
        this.resultado = resultado;
    }

    public String mostrarResultado() {
        return equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre() + ": " + resultado + 
               " - Estadio: " + estadio.getNombre() + ", Ciudad: " + estadio.getCiudad();
    }
}

class Grupo {
    private String nombre;
    private List<Equipo> equipos;

    public Grupo(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Grupo: ").append(nombre).append("\n");
        for (Equipo equipo : equipos) {
            info.append("  - Equipo: ").append(equipo.getNombre()).append("\n");
        }
        return info.toString();
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }
}

class Estadio {
    private String nombre;
    private String ciudad;
    private int capacidad;

    public Estadio(String nombre, String ciudad, int capacidad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }
}

class Mundial {
    private List<Grupo> grupos;
    private List<Estadio> estadios;

    public Mundial() {
        this.grupos = new ArrayList<>();
        this.estadios = new ArrayList<>();
    }

    public void registrarGrupo(Grupo grupo) {
        grupos.add(grupo);
    }

    public void registrarEstadio(Estadio estadio) {
        estadios.add(estadio);
    }

    public Equipo obtenerEquipoPorNombre(String nombreEquipo) {
        for (Grupo grupo : grupos) {
            for (Equipo equipo : grupo.getEquipos()) {
                if (equipo.getNombre().equals(nombreEquipo)) {
                    return equipo;
                }
            }
        }
        return null;
    }

    public List<Estadio> obtenerEstadios() {
        return estadios;
    }

    public List<Grupo> obtenerGrupos() {
        return grupos;
    }

    public void generarEquiposAleatorios() {
        String[] nombresEquipos = {
            "Argentina", "Brasil", "Chile", "Uruguay",
            "Francia", "Alemania", "España", "Portugal",
            "Inglaterra", "Italia", "Holanda", "Bélgica",
            "Croacia", "Serbia", "México", "Estados Unidos"
        };

        String[] nombresEntrenadores = {
            "Lionel Scaloni", "Tite", "Reinaldo Rueda", "Óscar Tabárez",
            "Didier Deschamps", "Joachim Löw", "Luis Enrique", "Fernando Santos",
            "Gareth Southgate", "Roberto Mancini", "Frank de Boer", "Roberto Martínez",
            "Zlatko Dalić", "Dragan Stojković", "Gerardo Martino", "Gregg Berhalter"
        };

        String[] posiciones = {"Portero", "Defensa", "Centrocampista", "Delantero"};

        List<Equipo> equipos = new ArrayList<>();
        Random random = new Random();

        for (String nombreEquipo : nombresEquipos) {
            String entrenador = nombresEntrenadores[random.nextInt(nombresEntrenadores.length)];
            Equipo equipo = new Equipo(nombreEquipo, entrenador);
            for (int i = 0; i < random.nextInt(6) + 18; i++) {
                String nombreJugador = "Jugador " + (random.nextInt(1000) + 1);
                int edadJugador = random.nextInt(21) + 18;
                String posicionJugador = posiciones[random.nextInt(posiciones.length)];
                Jugador jugador = new Jugador(nombreJugador, edadJugador, posicionJugador);
                equipo.agregarJugador(jugador);
            }
            equipos.add(equipo);
        }

        Collections.shuffle(equipos);

        for (int i = 0; i < equipos.size(); i += 4) {
            Grupo grupo = new Grupo("Grupo " + (char)('A' + i / 4));
            for (int j = i; j < i + 4 && j < equipos.size(); j++) {
                grupo.agregarEquipo(equipos.get(j));
            }
            grupos.add(grupo);
        }
    }

    public void generarEstadios() {
        Object[][] nombresEstadios = {
            {"Maracaná", "Río de Janeiro", 78000},
            {"Wembley", "Londres", 90000},
            {"Camp Nou", "Barcelona", 99000},
            {"Santiago Bernabéu", "Madrid", 81000},
            {"San Siro", "Milán", 80018},
            {"Estadio Azteca", "Ciudad de México", 87000},
            {"Allianz Arena", "Múnich", 75000},
            {"Old Trafford", "Manchester", 75643}
        };

        for (Object[] estadioInfo : nombresEstadios) {
            Estadio estadio = new Estadio((String)estadioInfo[0], (String)estadioInfo[1], (Integer)estadioInfo[2]);
            estadios.add(estadio);
        }
    }

    public String mostrarGrupos() {
        StringBuilder infoGrupos = new StringBuilder();
        for (Grupo grupo : grupos) {
            infoGrupos.append(grupo.mostrarInfo()).append("\n");
        }
        return infoGrupos.toString();
    }
}

public class MundialApp extends JFrame {
    private Mundial mundial;
    private JTextArea textMostrarGrupos;
    private JComboBox<String> comboElegirEquipo;
    private JTextArea textInfoEquipo;

    public MundialApp() {
        mundial = new Mundial();
        mundial.generarEquiposAleatorios();
        mundial.generarEstadios();

        setTitle("Gestión de Mundial");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel(new BorderLayout());
        JLabel labelMostrarGrupos = new JLabel("Grupos del Mundial");
        panelNorte.add(labelMostrarGrupos, BorderLayout.NORTH);

        textMostrarGrupos = new JTextArea(10, 50);
        textMostrarGrupos.setEditable(false);
        JScrollPane scrollMostrarGrupos = new JScrollPane(textMostrarGrupos);
        panelNorte.add(scrollMostrarGrupos, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new FlowLayout());
        JLabel labelElegirEquipo = new JLabel("Elegir Equipo:");
        panelCentro.add(labelElegirEquipo);

        comboElegirEquipo = new JComboBox<>();
        panelCentro.add(comboElegirEquipo);

        JButton botonMostrarInfo = new JButton("Mostrar Información del Equipo");
        botonMostrarInfo.addActionListener(e -> mostrarInfo());
        panelCentro.add(botonMostrarInfo);

        add(panelCentro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        JLabel labelInfoEquipo = new JLabel("Información del Equipo");
        panelSur.add(labelInfoEquipo, BorderLayout.NORTH);

        textInfoEquipo = new JTextArea(20, 50);
        textInfoEquipo.setEditable(false);
        JScrollPane scrollInfoEquipo = new JScrollPane(textInfoEquipo);
        panelSur.add(scrollInfoEquipo, BorderLayout.CENTER);

        add(panelSur, BorderLayout.SOUTH);

        actualizarMostrarGrupos();
        cargarComboboxEquipos();
        simularPartidos();
    }

    private void actualizarMostrarGrupos() {
        textMostrarGrupos.setText(mundial.mostrarGrupos());
    }

    private void cargarComboboxEquipos() {
        for (Grupo grupo : mundial.obtenerGrupos()) {
            for (Equipo equipo : grupo.getEquipos()) {
                comboElegirEquipo.addItem(equipo.getNombre());
            }
        }
    }

    private void simularPartidos() {
        Random random = new Random();
        List<Estadio> estadios = mundial.obtenerEstadios();

        for (Grupo grupo : mundial.obtenerGrupos()) {
            List<Equipo> equipos = grupo.getEquipos();
            for (int i = 0; i < equipos.size(); i++) {
                for (int j = i + 1; j < equipos.size(); j++) {
                    Equipo equipoLocal = equipos.get(i);
                    Equipo equipoVisitante = equipos.get(j);
                    Estadio estadio = estadios.get(random.nextInt(estadios.size()));
                    String resultado = random.nextInt(5) + "-" + random.nextInt(5);
                    Partido partido = new Partido(equipoLocal, equipoVisitante, estadio, resultado);
                    equipoLocal.agregarPartido(partido);
                    equipoVisitante.agregarPartido(partido);
                }
            }
        }
    }

    private void mostrarInfo() {
        String equipoSeleccionado = (String) comboElegirEquipo.getSelectedItem();

        if (equipoSeleccionado == null || equipoSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un equipo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Equipo equipo = mundial.obtenerEquipoPorNombre(equipoSeleccionado);

        if (equipo == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el equipo seleccionado.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder info = new StringBuilder(equipo.mostrarInfo());
        info.append("\nHistorial de Partidos:\n");
        for (Partido partido : equipo.getHistorialPartidos()) {
            info.append(partido.mostrarResultado()).append("\n");
        }

        textInfoEquipo.setText(info.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MundialApp app = new MundialApp();
            app.setVisible(true);
        });
    }
}