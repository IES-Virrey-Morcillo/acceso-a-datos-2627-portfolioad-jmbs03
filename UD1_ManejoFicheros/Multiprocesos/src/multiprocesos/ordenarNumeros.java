/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiprocesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Arrays;

/**
 *
 * @author josema
 */
public class ordenarNumeros {

    public static void main(String[] args) {

        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(isr);

            String lineaTeclado = null;
            while ((lineaTeclado = bf.readLine()) != null) {
                String datos[] = lineaTeclado.split(" ");
                int numeros[] = new int[datos.length];
                for (int i = 0; i < numeros.length; i++) {
                    numeros[i] = Integer.parseInt(datos[i]);
                }
                Arrays.sort(numeros);
                for (int i = 0; i < numeros.length; i++) {
                    System.out.print(numeros[i] + " ");
                }
                System.out.println("");
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
}
