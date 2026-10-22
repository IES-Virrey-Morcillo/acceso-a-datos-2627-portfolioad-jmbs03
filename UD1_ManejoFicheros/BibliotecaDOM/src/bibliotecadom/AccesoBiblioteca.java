/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecadom;

import java.io.File;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;

/**
 *
 * @author josema
 */
public class AccesoBiblioteca {
    public static void main(String[] args) {
        
        try {
            BibliotecaDOM biblioteca = new BibliotecaDOM(new File("biblioteca.xml"), new File("biblioteca.xsd"));
            
            biblioteca.mostrarBiblioteca("");
            System.out.println("****Filtrando por pelicula");
            biblioteca.mostrarBiblioteca("pelicula");
            
            ArrayList<String> autoresVacio = new ArrayList();
            ArrayList<String> autores = new ArrayList();
            biblioteca.insertarLibro("Misterios de DAW", "IESVM", "2026-10-20", autoresVacio);
            autores.add("Denis Ignat");
            autores.add("Denis Gandrabur");            
            autores.add("Pedro");            
            biblioteca.insertarLibro("Misterios de DAW", "IESVM", "2026-10-20", autores);
            autores.add("Reda");            
            biblioteca.insertarLibro("Misterios de DAM", "IESVM", "2026-10-20", autores);
            System.out.println("**** TRAS INSERTAR ****");
            biblioteca.mostrarBiblioteca("libro");
            
            System.out.println("***MODIFICO PELICULA***");
            biblioteca.modificarProductoraPelicula("Los otros", "New Line Cinema");
            biblioteca.modificarProductoraPelicula("Los otros", "New Line Cinema");
            biblioteca.modificarProductoraPelicula("Esta peli no esta", "New Line Cinema");
            biblioteca.modificarProductoraPelicula(null, "New Line Cinema");
            biblioteca.modificarProductoraPelicula(null, null);
            biblioteca.mostrarBiblioteca("pelicula");
            
            
            
            biblioteca.guardar(new File("bibliotecaModificado.xml"), new File("biblioteca.xsd"));
        } catch (TransformerException ex) {
            System.getLogger(AccesoBiblioteca.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
