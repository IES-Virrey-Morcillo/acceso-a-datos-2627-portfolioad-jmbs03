/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivostexto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;

public class Ejemplo5 {

    public static void main(String[] args) {
        try {
            // Definir la ruta de la carpeta
            Path path = Paths.get("C:\\Users\\pablo\\hola");
            System.out.println("Si sale esto, Path no hace saltar excepción");
            // Crear la carpeta
            Files.createDirectory(path);
            System.out.println("Carpeta creada con éxito");
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Carpeta ya existe");
        } catch (IOException ex) {
            System.out.println("Otro error IO: " + ex.toString() + ex.getMessage() );
        }

        Path path = Paths.get("nueva/ruta/del/directorio");
        Path path2 = Paths.get("nueva/ruta/del2/dam");
        Path path3 = Paths.get("nueva/ruta/del2/daw");

        try {
            // Crear la estructura de directorios si no existe
            Files.createDirectories(path);
            Files.createDirectories(path2);
            Files.createDirectories(path3);
            System.out.println("Carpetas creadas con exito");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
        // Ruta de origen
        Path origen = Paths.get("alumnosMedia.txt");
        // Ruta de destino
        Path destino
                = Paths.get("nueva/ruta/del/directorio/archivo_destino.txt");


    }
    
    public static void moverArhivo(Path origen, Path destino) {
        try {
        // Mover el archivo a la nueva ubicación
            Files.move(origen, destino);
            System.out.println("Archivo movido con éxito.");
        } catch (NoSuchFileException ex) {
            System.out.println("El archivo origen no existe");
        } catch (FileAlreadyExistsException ex) {
            
            
        }       
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
