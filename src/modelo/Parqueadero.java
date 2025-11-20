package modelo;

import java.util.ArrayList;

/**
 * Clase que representa el parqueadero completo.
 * Maneja todos los espacios disponibles para vehiculos.
 *
 * @author Velez
 * @version 1.0
 */
public class Parqueadero {

    /**
     * Nombre del parqueadero
     */
    private String nombre;

    /**
     * Lista de espacios del parqueadero
     */
    private ArrayList<Espacio> espacios;

    /**
     * Capacidad total del parqueadero
     */
    private int capacidadTotal;

    /**
     * Constructor de la clase Parqueadero
     *
     * @param nombre El nombre del parqueadero
     * @param capacidadTotal La capacidad total de espacios
     */
    public Parqueadero(String nombre, int capacidadTotal) {
        this.nombre = nombre;
        this.capacidadTotal = capacidadTotal;
        this.espacios = new ArrayList<Espacio>();
    }

    /**
     * Obtiene el nombre del parqueadero
     *
     * @return El nombre del parqueadero
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del parqueadero
     *
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de espacios
     *
     * @return La lista de espacios
     */
    public ArrayList<Espacio> getEspacios() {
        return espacios;
    }

    /**
     * Establece la lista de espacios
     *
     * @param espacios La nueva lista de espacios
     */
    public void setEspacios(ArrayList<Espacio> espacios) {
        this.espacios = espacios;
    }

    /**
     * Obtiene la capacidad total del parqueadero
     *
     * @return La capacidad total
     */
    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    /**
     * Establece la capacidad total del parqueadero
     *
     * @param capacidadTotal La nueva capacidad
     */
    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    /**
     * Agrega un espacio al parqueadero
     *
     * @param espacio El espacio a agregar
     * @return true si se agrego correctamente, false si no hay capacidad
     */
    public boolean agregarEspacio(Espacio espacio) {
        if (espacios.size() < capacidadTotal) {
            espacios.add(espacio);
            return true;
        }
        return false;
    }

    /**
     * Busca un espacio disponible para un tipo de vehiculo
     *
     * @param tipoVehiculo El tipo de vehiculo (AUTO o MOTO)
     * @return El espacio disponible o null si no hay
     */
    public Espacio buscarEspacioDisponible(String tipoVehiculo) {
        for (int i = 0; i < espacios.size(); i++) {
            Espacio espacio = espacios.get(i);
            if (espacio.estaDisponible() && espacio.getTipoVehiculo().equals(tipoVehiculo)) {
                return espacio;
            }
        }
        return null;
    }

    /**
     * Cuenta cuantos espacios estan disponibles
     *
     * @return El numero de espacios disponibles
     */
    public int contarEspaciosDisponibles() {
        int disponibles = 0;
        for (int i = 0; i < espacios.size(); i++) {
            if (espacios.get(i).estaDisponible()) {
                disponibles++;
            }
        }
        return disponibles;
    }

    /**
     * Cuenta cuantos espacios estan ocupados
     *
     * @return El numero de espacios ocupados
     */
    public int contarEspaciosOcupados() {
        int ocupados = 0;
        for (int i = 0; i < espacios.size(); i++) {
            if (espacios.get(i).isOcupado()) {
                ocupados++;
            }
        }
        return ocupados;
    }

    /**
     * Busca un espacio por numero
     *
     * @param numero El numero del espacio a buscar
     * @return El espacio encontrado o null si no existe
     */
    public Espacio buscarEspacioPorNumero(int numero) {
        for (int i = 0; i < espacios.size(); i++) {
            if (espacios.get(i).getNumero() == numero) {
                return espacios.get(i);
            }
        }
        return null;
    }

    /**
     * Muestra la informacion del parqueadero
     *
     * @return String con la informacion del parqueadero
     */
    public String mostrarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("\n======== PARQUEADERO ").append(nombre).append(" ========\n");
        info.append("Capacidad Total: ").append(capacidadTotal).append("\n");
        info.append("Espacios Disponibles: ").append(contarEspaciosDisponibles()).append("\n");
        info.append("Espacios Ocupados: ").append(contarEspaciosOcupados()).append("\n");
        info.append("================================\n");

        return info.toString();
    }
}