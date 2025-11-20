package utilidad;

/**
 * Clase utilitaria para validar datos del sistema de parqueadero.
 * Contiene metodos estaticos para validacion de placas y otros datos.
 *
 * @author Velez
 * @version 1.0
 */
public class Validador {

    /**
     * Valida si una placa tiene el formato correcto
     * La placa debe tener al menos 6 caracteres
     *
     * @param placa La placa a validar
     * @return true si la placa es valida, false si no
     */
    public static boolean validarPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return false;
        }

        // Eliminar espacios
        placa = placa.trim().toUpperCase();

        // La placa debe tener al menos 6 caracteres
        if (placa.length() < 6) {
            return false;
        }

        return true;
    }

    /**
     * Identifica el tipo de vehiculo segun su placa.
     * Si la placa termina en letra, es una MOTO.
     * Si la placa termina en numero, es un AUTO.
     *
     * @param placa La placa del vehiculo
     * @return "MOTO" si termina en letra, "AUTO" si termina en numero, null si es invalida
     */
    public static String identificarTipoVehiculo(String placa) {
        if (!validarPlaca(placa)) {
            return null;
        }

        // Eliminar espacios y convertir a mayusculas
        placa = placa.trim().toUpperCase();

        // Obtener el ultimo caracter de la placa
        char ultimoCaracter = placa.charAt(placa.length() - 1);

        // Verificar si es letra o numero
        if (Character.isLetter(ultimoCaracter)) {
            return "MOTO";
        } else if (Character.isDigit(ultimoCaracter)) {
            return "AUTO";
        }

        return null;
    }

    /**
     * Valida que un numero sea positivo
     *
     * @param numero El numero a validar
     * @return true si es positivo, false si no
     */
    public static boolean validarNumeroPositivo(int numero) {
        return numero > 0;
    }

    /**
     * Valida que un texto no este vacio
     *
     * @param texto El texto a validar
     * @return true si no esta vacio, false si esta vacio
     */
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}