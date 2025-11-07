package Modelo;

public class ApuestaPar extends ApuestaBase {

    public ApuestaPar(double monto) {
        super(monto,'P');
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) {
            return false;
        }
        return numero % 2 == 0;
    }
}
