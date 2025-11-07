package Modelo;

public class ApuestaNegro extends ApuestaBase {

    public ApuestaNegro(double monto) {
        super(monto,'N');
    }

    @Override
    public boolean acierta(int numero, String color) {
        if (numero == 0) {
            return false;
        }
        return "NEGRO".equals(color);
    }
}
