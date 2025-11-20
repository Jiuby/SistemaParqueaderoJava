package modelo;

import java.time.LocalDateTime;

/**
 * Clase abstracta que representa un vehiculo en el parqueadero.
 * Esta clase es la base para los diferentes tipos de vehiculos.
 *
 * @author Velez
 * @version 1.0
 */
public abstract class Vehiculo {

    /**
     * Placa del vehiculo
     */
    private String placa;

    /**
     * Tipo de vehiculo (AUTO o MOTO)
     */
    private String tipo;

    /**
     * Fecha y hora de entrada al parqueadero
     */
    private LocalDateTime horaEntrada;

    /**
     * Constructor de la clase Vehiculo
     *
     * @param placa La placa del vehiculo
     * @param tipo El tipo de vehiculo
     */
    public Vehiculo(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = LocalDateTime.now();
    }

    /**
     * Obtiene la placa del vehiculo
     *
     * @return La placa del vehiculo
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa del vehiculo
     *
     * @param placa La nueva placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene el tipo de vehiculo
     *
     * @return El tipo de vehiculo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de vehiculo
     *
     * @param tipo El nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la hora de entrada del vehiculo
     *
     * @return La hora de entrada
     */
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * Establece la hora de entrada del vehiculo
     *
     * @param horaEntrada La nueva hora de entrada
     */
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * Muestra la informacion del vehiculo
     * Este metodo debe ser implementado por las clases hijas
     *
     * @return String con la informacion del vehiculo
     */
    public abstract String mostrarInformacion();
}