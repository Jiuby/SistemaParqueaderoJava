package modelo;

/**
 * Clase que representa un espacio de parqueo en el parqueadero.
 * Cada espacio puede estar ocupado o disponible.
 *
 * @author Velez
 * @version 1.0
 */
public class Espacio {

    /**
     * Numero identificador del espacio
     */
    private int numero;

    /**
     * Indica si el espacio esta ocupado (true) o disponible (false)
     */
    private boolean ocupado;

    /**
     * Tipo de vehiculo que puede ocupar este espacio (AUTO o MOTO)
     */
    private String tipoVehiculo;

    /**
     * Vehiculo que esta ocupando el espacio actualmente
     */
    private Vehiculo vehiculoActual;

    /**
     * Constructor de la clase Espacio
     *
     * @param numero El numero del espacio
     * @param tipoVehiculo El tipo de vehiculo permitido
     */
    public Espacio(int numero, String tipoVehiculo) {
        this.numero = numero;
        this.tipoVehiculo = tipoVehiculo;
        this.ocupado = false;
        this.vehiculoActual = null;
    }

    /**
     * Obtiene el numero del espacio
     *
     * @return El numero del espacio
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el numero del espacio
     *
     * @param numero El nuevo numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Verifica si el espacio esta ocupado
     *
     * @return true si esta ocupado, false si esta disponible
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     * Establece el estado de ocupacion del espacio
     *
     * @param ocupado El nuevo estado
     */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    /**
     * Obtiene el tipo de vehiculo del espacio
     *
     * @return El tipo de vehiculo
     */
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Establece el tipo de vehiculo del espacio
     *
     * @param tipoVehiculo El nuevo tipo
     */
    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    /**
     * Obtiene el vehiculo que ocupa actualmente el espacio
     *
     * @return El vehiculo actual o null si esta vacio
     */
    public Vehiculo getVehiculoActual() {
        return vehiculoActual;
    }

    /**
     * Establece el vehiculo que ocupa el espacio
     *
     * @param vehiculoActual El vehiculo a colocar
     */
    public void setVehiculoActual(Vehiculo vehiculoActual) {
        this.vehiculoActual = vehiculoActual;
    }

    /**
     * Ocupa el espacio con un vehiculo
     *
     * @param vehiculo El vehiculo que ocupara el espacio
     */
    public void ocupar(Vehiculo vehiculo) {
        this.ocupado = true;
        this.vehiculoActual = vehiculo;
    }

    /**
     * Libera el espacio, dejandolo disponible
     */
    public void liberar() {
        this.ocupado = false;
        this.vehiculoActual = null;
    }

    /**
     * Verifica si el espacio esta disponible
     *
     * @return true si esta disponible, false si esta ocupado
     */
    public boolean estaDisponible() {
        return !ocupado;
    }

    /**
     * Muestra la informacion del espacio
     *
     * @return String con la informacion del espacio
     */
    public String mostrarInformacion() {
        String estado = ocupado ? "OCUPADO" : "DISPONIBLE";
        String info = "Espacio #" + numero + " [" + tipoVehiculo + "] - " + estado;

        if (ocupado && vehiculoActual != null) {
            info += " - Placa: " + vehiculoActual.getPlaca();
        }

        return info;
    }
}