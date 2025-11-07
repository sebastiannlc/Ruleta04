package Utilidades;

import Modelo.ApuestaBase;
import Modelo.ApuestaRojo;
import Modelo.ApuestaNegro;
import Modelo.ApuestaPar;
import Modelo.ApuestaImpar;

public class ApuestaFactory {

    public static ApuestaBase crearApuesta(char etiqueta, double monto) {
        return switch (etiqueta) {
            case 'R' -> new ApuestaRojo(monto);
            case 'N' -> new ApuestaNegro(monto);
            case 'P' -> new ApuestaPar(monto);
            case 'I' -> new ApuestaImpar(monto);
            default -> throw new IllegalArgumentException("Etiqueta de apuesta inválida.");
        };
    }
}