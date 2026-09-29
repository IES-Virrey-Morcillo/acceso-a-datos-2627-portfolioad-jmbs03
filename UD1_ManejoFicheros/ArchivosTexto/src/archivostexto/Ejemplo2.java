/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivostexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josema
 */
public class Ejemplo2 {

    public static void main(String args[]) {
        try {
            
            String nombre, apellido, entrada;
            int edad;
            double estatura;
            boolean vacunado;
            ArrayList<String> listado = new ArrayList();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            // BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Nombre:");
            listado.add(br.readLine());
            nombre = listado.getLast();
            System.out.println("Apellido:");
            listado.add(br.readLine());
            apellido = listado.getLast();
            System.out.println("Edad:");
            listado.add(br.readLine());
            edad = Integer.parseInt(listado.getLast());
            System.out.println("Estatura:");
            listado.add(br.readLine());
            estatura = Double.parseDouble(listado.getLast());
            System.out.println("Vacunado:");
            listado.add(br.readLine());
            vacunado = Boolean.parseBoolean(listado.getLast());
            
            
            System.out.println("Sus datos son " + nombre + " " + apellido + " " + edad + " " + estatura + " " + vacunado);
            
            
            FileWriter fw = new FileWriter("infoPacientes.csv",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            String lineaNueva;// = nombre + ";" + apellido + ";" + edad + ";" + estatura + ";" + vacunado;
            lineaNueva = String.join(";", listado);
            pw.println(lineaNueva);
            pw.close();
            br.close();
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida");
        }
    }
}
