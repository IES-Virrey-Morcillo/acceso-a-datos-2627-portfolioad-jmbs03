/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplosdom;

import java.io.File;
import org.w3c.dom.Node;

/**
 *
 * @author josema
 */
public class EjemplosDOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        AccesoDOM ad = new AccesoDOM();

        int resultado = ad.abriXMLaDOM(new File("libros.xml"));

        System.out.println("Fichero abierto OK?: " + resultado);
        if (resultado == 0) {
            ad.recorreDOMyMuestra();
            System.out.println("Libros posteriores al 2000");
            ad.librosPosterioresA(2000);
            System.out.println("Libros posteriores al 1800");
            ad.librosPosterioresA(1800);

            ad.insertarLibroEnDOM("Cien años de soledad", "Gabriel García Márquez", "1967");
            ad.insertarLibroEnDOM("La sombra del viento", "Carlos Ruiz Zafón", "2004");
            ad.insertarLibroEnDOM("El caballero de los siete reinos", "George RR Martin", "1998");
            ad.insertarLibroEnDOM("El caballero de los siete reinos", "George RR Martin", "1998");
            ad.insertarLibroEnDOM("El caballero de los siete reinos", "George RR Martin", "1998");
            ad.insertarLibroEnDOM("El caballero de los siete reinos", "George RR Martin", "1998");
            ad.insertarLibroEnDOM("Los renglones torcidos de Dios", "Torcuato Luca de Tena", "1978");

            System.out.println("DESPUÉS DE INSERTAR!!!");
            ad.recorreDOMyMuestra();

            ad.deleteNode("El caballero de los siete reinos");
            ad.deleteNode("El caballero de los siete reinos");

            System.out.println("DESPUÉS DE BORRAR!!!");
            ad.recorreDOMyMuestra();

            ad.borrarPorAnio(2003);

            System.out.println("DESPUÉS DE BORRAR POR AÑO!!!");
            ad.recorreDOMyMuestra();

            ad.modificarAutor("Don Quijote", "Alejandro Morales");
            ad.modificarAutor("Unicornia", "Alejandro Morales");
            System.out.println("¡¡¡DESPUÉS DE MODIFICAR AUTOR!!!");
            ad.recorreDOMyMuestra();

            ad.modificarAutorV2("Los renglones torcidos de Dios", "JMBS");
            ad.modificarAutorV2("Unicornia", "Alejandro Morales");
            System.out.println("¡¡¡DESPUÉS DE MODIFICAR AUTOR V2!!!");
            ad.recorreDOMyMuestra();
            ad.guardarDOMcomoArchivo("librosModificado.xml");
        }
    }

}
