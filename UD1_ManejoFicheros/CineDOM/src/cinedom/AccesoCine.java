/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinedom;

import java.io.File;
import javax.xml.transform.TransformerException;

/**
 *
 * @author josema
 */
public class AccesoCine {
    
    /**
     * 
     * @param args argumentos de llamada individual
     */
    public static void main(String[] args) {
        
        try {
            File archivo = new File("cine.xml");
            File archivoMod = new File("cineModificado.xml");
            File esquema = new File("cine.xsd");
            
            CineDOM cine = new CineDOM(archivo, esquema);
            
            cine.muestraDOM();
            
            cine.muestraCarterleraIdioma("inglés");
            cine.muestraCarterleraIdioma("");
            cine.muestraCarterleraIdioma(null);
            
            cine.mostrarCarteleraPorIdioma2("castellano");
            cine.mostrarCarteleraPorIdioma2("");
            
            cine.averagePrice();
            
            
            cine.guardar(archivoMod, esquema);
        } catch (TransformerException ex) {
            System.out.println("Error al transformar XML");
        }
        
    }
}
