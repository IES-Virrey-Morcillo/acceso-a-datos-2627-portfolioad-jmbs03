/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package archivostexto;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josema
 */
public class ArchivosTexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        try {
            // Objeto para leer de fichero.
            Scanner sc = new Scanner(new FileReader("alumnos.txt"));
            // Objetos para escribir en fichero
            FileWriter fw = new FileWriter("alumnosMedia.txt",false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            
            FileWriter fwlog = new FileWriter("errores.log",true);
            BufferedWriter bwlog = new BufferedWriter(fwlog);
            PrintWriter log = new PrintWriter(bwlog);
            
            while(sc.hasNext()) {
                String linea = sc.nextLine();
                String[] datos = linea.split(":");
                
                if (datos.length == 4) {
                    System.out.println("Nombre: " + datos[0] + " Nota Primera " + datos[1] + 
                            " Nota Segunda " + datos[2] + " Nota tercera " + datos[3]);
                    try {
                        int suma = Integer.valueOf(datos[1]) + 
                                   Integer.valueOf(datos[2]) + 
                                   Integer.parseInt(datos[3]);  
                        float media = (float) suma / 3;
                    
                        salida.println(datos[0]+":"+media);
                    } catch(NumberFormatException e) {
                        System.out.println("NUMERO NO RECONOCIDO: " + linea);
                        log.println(LocalDateTime.now()+"|NUMERONORECONOCIDO|"+linea);
                    }                  

                } else {
                    System.out.println("FORMATO NO RECONOCIDO " +linea);
                    log.println(LocalDateTime.now() + "|NUMEROCOLUMNASINCORRECTO|"+linea);
                }
            }
            System.out.println("Fin fichero");
            sc.close();
            salida.close();
            log.close();
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no existe");
        } catch (IOException ex) {
            Logger.getLogger(ArchivosTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
