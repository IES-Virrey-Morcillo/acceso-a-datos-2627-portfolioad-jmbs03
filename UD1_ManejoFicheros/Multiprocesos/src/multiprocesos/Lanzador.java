/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package multiprocesos;

import java.io.File;

/**
 *
 * @author josema
 */
public class Lanzador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // 1. Fichero temporal para conectar ambos procesos
        File tempFile = new File("numeros.txt");

        // 2. Primer proceso: aleatorios -> escribe en el fichero
        ProcessBuilder pbAleatorios = new ProcessBuilder("java", "Aleatorios");
        pbAleatorios.redirectOutput(tempFile); // redirige salida a fichero
        Process p1 = pbAleatorios.start();
        p1.waitFor(); // esperamos a que termine antes de seguir

        // 3. Segundo proceso: ordenarNumeros -> lee desde el fichero
        ProcessBuilder pbOrdenar = new ProcessBuilder("java", "ordenarNumeros");
        pbOrdenar.redirectInput(tempFile); // redirige entrada desde fichero
        pbOrdenar.inheritIO(); // hereda la consola del programa lanzador
        Process p2 = pbOrdenar.start();
        p2.waitFor();

        System.out.println("Procesos finalizados. Revisa la salida en pantalla.");
    }
    
}
