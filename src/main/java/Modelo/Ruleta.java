package Modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ruleta {

    private static final Random RANDOM = new Random();
    private static final Map<Integer, String> COLORES_RULETA = new HashMap<>();

    static {
        COLORES_RULETA.put(0, "VERDE");

        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int n : rojos) {
            COLORES_RULETA.put(n, "ROJO");
        }

        for (int i = 1; i <= 36; i++) {
            if (!COLORES_RULETA.containsKey(i)) {
                COLORES_RULETA.put(i, "NEGRO");
            }
        }
    }

    public static int girar() {
        return RANDOM.nextInt(37);
    }

    public static String getColor(int numero) {
        return COLORES_RULETA.getOrDefault(numero, "VERDE");
    }

    // Los métodos esRojo y esPar se mantienen solo para uso interno de la jerarquía de apuestas
    public static boolean esRojo(int numero) {
        return "ROJO".equals(getColor(numero));
    }

    public static boolean esPar(int numero) {
        return numero % 2 == 0;
    }
}