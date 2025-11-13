package Interfaces;

import Modelo.Resultado;
import java.util.List;

public interface IRepositorioResultados {

    void agregarResultado(Resultado resultado);

    List<Resultado> obtenerHistorialGlobal();
}