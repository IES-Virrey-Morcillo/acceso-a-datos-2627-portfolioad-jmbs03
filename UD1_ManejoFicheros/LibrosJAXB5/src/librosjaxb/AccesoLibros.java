/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librosjaxb;

import java.io.File;

/**
 *
 * @author josema
 */
public class AccesoLibros {
    
    public static void main(String[] args) {
        
        System.out.println(System.getProperty("os.name"));
        LibrosJAXB libros = new LibrosJAXB(new File("Libros.xml"));
        
        libros.mostrarLibros();
        
        libros.insertarLibro("La sombra del viento", "Carlos Ruiz Zafón", Short.valueOf("2003"));
        libros.insertarLibro("Marina", "Carlos Ruiz Zafón", Short.valueOf("1998"));
        libros.insertarLibro("La ciudad de la media noche", "Carlos Ruiz Zafón", Short.valueOf("1995"));
        
        libros.mostrarV2("");
        System.out.println("OTRA LLAMADA");
        
        //libros.modificarLibro("La sombra del viento", "", "Alejandro Morales", null);
        
        
        //libros.eliminarLibro("La sombra del viento");
        
        libros.mostrarV2("");
        //libros.eliminarLibros("Carlos Ruiz Zafón");
        libros.eliminarLibros("No existe");
        libros.eliminarLibros(null);
        
        libros.ordenarLibreria("titulo");
        libros.mostrarV2("");
        libros.ordenarLibreria("publicado");
        libros.mostrarV2("");
        libros.ordenarLibreria("autorDESC");
        libros.mostrarV2("");
        libros.ordenarLibreria("tituloDESC");
        libros.mostrarV2("");
        libros.ordenarLibreria("");
        libros.mostrarV2("");
        
        libros.creaXMLdeLibros("librosModificado.xml");
    }
    
}
