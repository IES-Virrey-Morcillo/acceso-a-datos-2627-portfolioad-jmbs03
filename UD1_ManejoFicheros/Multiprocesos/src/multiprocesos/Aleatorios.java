/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiprocesos;

/**
 *
 * @author josema
 */
public class Aleatorios {

    public static void main(String[] args) {

        int cantidadGenerados = 40;

        for (int i = 0; i < cantidadGenerados; i++) {
            System.out.print(generaNumeroAleatorio(0, 100) + " ");
        }
    }

    public static int generaNumeroAleatorio(int minimo, int maximo) {
        int num = (int) (Math.random() * (maximo - minimo + 1) + (minimo));
        return num;
    }
}
