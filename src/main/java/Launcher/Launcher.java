package Launcher;

import Controlador.ResultadoController;
import Persistencia.RepositorioEnMemoria;
import Persistencia.RepositorioArchivo;
import Interfaces.IRepositorioResultados;
import Vista.VentanaLogin;
import javax.swing.SwingUtilities;

public class Launcher {

    public static void main(String[] args) {

        RepositorioArchivo repoArchivo = new RepositorioArchivo();
        RepositorioEnMemoria repoMemoria = new RepositorioEnMemoria();

        ResultadoController controladorResultados = new ResultadoController(repoArchivo, repoMemoria);

        new VentanaLogin().mostrarVentana();
    }
}