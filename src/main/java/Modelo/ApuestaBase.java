package Modelo;

import java.io.Serializable;

public abstract class ApuestaBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final double monto;
    protected final char etiqueta; // R, N, P, I

    public ApuestaBase(double monto, char etiqueta) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo.");
        }
        this.monto = monto;
        this.etiqueta = etiqueta;
    }

    public abstract boolean acierta(int numero, String color);

    public double getMonto() {
        return monto;
    }

    public char getEtiqueta() {
        return etiqueta;
    }
}