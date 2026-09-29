/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivostexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josema
 */
public class Ejercicio2 {
    
    public static void main(String[] args) {
        
        try {
            Scanner sc = new Scanner(new FileReader("banco.txt"));
            
            FileWriter fw = new FileWriter("morosos.txt",false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] datos = linea.split(":");
                
                int saldo = Integer.valueOf(datos[2]);
                int deuda = Integer.valueOf(datos[3]);
                boolean moroso = Boolean.parseBoolean(datos[4]);
                
                // Solo escribo si el cliente ya es moroso.
                if (moroso) {
                    String salida = datos[1] + " debes la cantidad de " + deuda + 
                            " €, por lo que se te retirarán los cargos de la cuenta corriente en la que tienes " 
                            + saldo + ".";
                    
                    if (saldo < deuda) {
                        salida += "Como le falta la cantidad " + (deuda-saldo) + 
                                ", se le añadirá a la deuda un 10% de la diferencia (" + 
                                ((deuda-saldo)*0.1) + ").";
                    }
                    
                    pw.println(salida);
                } 
                
            }
             
            pw.close();
            sc.close();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejercicio2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
