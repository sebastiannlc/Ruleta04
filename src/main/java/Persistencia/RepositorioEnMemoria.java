package Persistencia;

import Interfaces.IRepositorioResultados;
import Modelo.Resultado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioEnMemoria implements IRepositorioResultados {

    private final List<Resultado> historial = new ArrayList<>();

    public static final int MAX_HISTORIAL = 100;

    @Override
    public void agregarResultado(Resultado resultado) {
        if (historial.size() < MAX_HISTORIAL) {
            this.historial.add(resultado);
        }
    }

    @Override
    public List<Resultado> obtenerHistorialGlobal() {
        return Collections.unmodifiableList(historial);
    }
}