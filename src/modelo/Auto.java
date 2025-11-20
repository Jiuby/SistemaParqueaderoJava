package modelo;

/**
 * Clase que representa un automovil en el parqueadero.
 * Hereda de la clase Vehiculo.
 * Los autos se identifican porque su placa termina en numero.
 *
 * @author Velez
 * @version 1.0
 */
public class Auto extends Vehiculo {

    /**
     * Constructor de la clase Auto
     *
     * @param placa La placa del auto
     */
    public Auto(String placa) {
        super(placa, "AUTO");
    }

    /**
     * Muestra la informacion del auto
     *
     * @return String con la informacion del auto
     */
    public String mostrarInformacion() {
        return "AUTO - Placa: " + getPlaca() + " - Entrada: " + getHoraEntrada();
    }
}