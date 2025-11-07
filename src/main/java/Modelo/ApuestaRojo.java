package Modelo;

public class ApuestaRojo extends ApuestaBase {

    public ApuestaRojo(double monto) {
        super(monto,'R');
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) {
            return false;
        }
        return "ROJO".equals(color);
    }
}
