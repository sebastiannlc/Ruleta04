package Modelo;

public class ApuestaImpar extends ApuestaBase {

    public ApuestaImpar(double monto) {
        super(monto,'I');
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) {
            return false;
        }
        return numero % 2 != 0;
    }
}
