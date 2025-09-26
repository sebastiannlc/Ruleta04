package Modelo;

import java.util.Random;

/**
 * Responsabilidad Única: Contiene la lógica pura del juego (girar y evaluar).
 */
public class Ruleta {

    public static final int MAX_NUMERO = 36;
    private static final int[] NUMEROS_ROJOS =
            {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private static final Random RNG = new Random();

    public static int girar() {
        return RNG.nextInt(MAX_NUMERO + 1);
    }

    public static boolean evaluarApuesta(int numero, char tipo) {
        if (numero == 0) return false;

        if (tipo == 'R') return esRojo(numero);
        if (tipo == 'N') return !esRojo(numero);
        if (tipo == 'P') return numero % 2 == 0;
        if (tipo == 'I') return numero % 2 != 0;

        return false;
    }

    public static boolean esRojo(int n) {
        for (int rojo : NUMEROS_ROJOS) {
            if (rojo == n) return true;
        }
        return false;
    }
}