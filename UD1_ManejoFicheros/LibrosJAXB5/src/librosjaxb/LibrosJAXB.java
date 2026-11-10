/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librosjaxb;

import generated.Libro;
import generated.Libros;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author josema
 */
public class LibrosJAXB {

    private JAXBContext contexto;
    private Unmarshaller u; // Hace referencia a la "raíz" del archivo XML 
    private Libros libros;
    //es una lista de los objetos "hijos" del elemento raiz, que son de tipo Libro private 
    List<Libro> lLibros;

    public LibrosJAXB(File f) {
        // El constructor rellena las variables de la clase
        // para que puedan ser usadas por otros métodos
        try {
            // UNMARSHALLING: al llamar a unmarshal, rellenaremos el objeto libros 
            // con el contenido del archivo XML  
            // Abrimos el contexto JAXB, que tiene el método createUnmarshaller
            contexto = JAXBContext.newInstance(Libros.class);
            // Creamos el unmarshaller u
            u = contexto.createUnmarshaller();
            // Hacemos el método unmarshal del archivo f y "se llena" la variable 
            // libros, que tendrá toda la información del archivo XML
            libros = (Libros) u.unmarshal(f);
            // Se crea una lista de objetos Libro que recorreremos para extraer la información
            // OPCIONAL
            lLibros = libros.getLibro();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void mostrarLibros() {
        /*
         * Recorre la lista de objetos libro que hay en memoria y muestra toda la información asociada
         */

        System.out.println("Numero de libros:" + lLibros.size() + "\n");
        //Extraemos la información con los métodos de la clase Libro y la mostramos
        // Usaremos la variable lib para recorrer la lista de objetos Libro
        Libro lib;
        for (int i = 0; i < lLibros.size(); i++) {
            lib = lLibros.get(i);//asignamos a l el valor del libro en la lista
            System.out.println("Titulo: " + lib.getTitulo());
            System.out.println("Autor: " + lib.getAutor());
            System.out.println("Publicado en: " + lib.getPublicado() + "\n-------------");
        }

    }

    public void mostrarV2(String filtro) {
        for (Libro l : lLibros) {
            if (filtro.isEmpty() || l.getAutor().equalsIgnoreCase(filtro)) {
                System.out.println("Titulo: " + l.getTitulo()
                        + " Autor: " + l.getAutor()
                        + " Fecha publicación: " + l.getPublicado());
            }
        }
    }

    public int insertarLibro(String titulo, String autor, Short fecha) {

        if (titulo == null || autor == null || fecha == null
                || titulo.isBlank() || autor.isBlank()) {
            return -1;
        }

        Libro libro = new Libro();
        libro.setAutor(autor);
        libro.setPublicado(fecha);
        libro.setTitulo(titulo);

        lLibros.add(libro);

        return 0;
    }

    public void creaXMLdeLibros(String fileName) {
        // MARSHALLING: crea un archivo XML file pasandole la variable que corresponde al raíz
        try {
            Marshaller m = contexto.createMarshaller();
            // Para tener indentaciones y saltos de línea en el XML creado
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // El segundo parámetro del método marshall permite redirigir la salida
            // a diferentes sitios: consola, fichero, String, Flujos de bytes, red...
            //m.marshal(libros, System.out);
            // Con FileOutputStream, creamos un archivo
            //m.marshal(libros, new FileOutputStream(fileName));
            // Con BufferedWriter/FileWriter hacemos lo mismo,pero controlando
            // el rendimiento.
            m.marshal(libros, new BufferedWriter(new FileWriter(fileName)));
            // Con BuferedWriter

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            System.out.println("La ruta especificada para el archivo XML no es correcta.");
        } catch (IOException ex) {
            System.out.println("Error entrada/salida");
        }
    }

    public int modificarLibro(String tituloBuscado, String titulo, String autor, Short fecha) {

        if (tituloBuscado == null || tituloBuscado.isEmpty()) {
            System.out.println("Entrada no válida");
            return -2;
        }

        if ((titulo == null || titulo.isBlank()) && (autor == null || autor.isBlank())
                && fecha == null) {
            System.out.println("No quieres modificar nada, ni lo busco");
            return 1;
        }

        for (Libro l : lLibros) {
            if (tituloBuscado.equals(l.getTitulo())) {
                if (titulo != null && !titulo.isBlank()) {
                    l.setTitulo(titulo);
                }
                if (autor != null && !autor.isBlank()) {
                    l.setAutor(autor);
                }
                if (fecha != null) {
                    l.setPublicado(fecha);
                }
                System.out.println("Se modifican los campos del libro");
                return 0;
            }
        }
        System.out.println("No se encuentra el libro: " + tituloBuscado);
        return -1;
    }

    public int eliminarLibro(String titulo) {

        for (Libro l : lLibros) {
            if (l.getTitulo().equals(titulo)) {
                lLibros.remove(l);
                System.out.println("Se elimina el libro");
                return 0;
            }
        }

        System.out.println("No encuentro el libro " + titulo);
        return -1;
    }

    public int eliminarLibros(String autor) {

        if (autor == null || autor.isBlank()) {
            System.out.println("Hay que informar el autor");
            return -2;
        }

        int librosBorrados = 0;
        for (int i = 0; i < lLibros.size(); i++) {
            if (lLibros.get(i).getAutor().equals(autor)) {
                System.out.println("Se elimina el libro " + lLibros.get(i).getTitulo());
                lLibros.remove(i);
                // Si se os olvida el i--, os saltaréis elementos de la lista...
                i--;
                librosBorrados++;
            }
        }

        if (librosBorrados == 0) {
            System.out.println("No encuentro libros de " + autor);
            return -1;
        } else {
            System.out.println("Se han borrado " + librosBorrados + " libros");
            return 0;
        }
    }

    public void ordenarLibreria(String modo) {
        
        Comparator<Libro> comparatorPorAno = new Comparator<Libro>() {
            @Override
            public int compare(Libro libro1, Libro libro2) {
                return Short.compare(libro1.getPublicado(), libro2.getPublicado());
            }
        };
        
        Comparator<Libro> comparatorPorTitulo = new Comparator<Libro>() {
            @Override
            public int compare(Libro libro1, Libro libro2) {
                return libro1.getTitulo().compareTo(libro2.getTitulo());
            }
        };
        
        switch(modo) {
            case "publicado" -> {
                System.out.println("Se ordena por fecha de publicacion");
                Collections.sort(lLibros, comparatorPorAno);
            }

            
            case "autor" -> {
                System.out.println("Se ordena por autor");
                lLibros.sort((l1,l2) -> l1.getAutor().compareTo(l2.getAutor()));
            }
            case "autorDESC" -> {
                System.out.println("Se ordena por autor DESC");
                lLibros.sort((l1,l2) -> l2.getAutor().compareTo(l1.getAutor()));
            }            
            case "titulo" -> {
                System.out.println("Se ordena por titulo");
                lLibros.sort(Comparator.comparing(Libro::getTitulo));
            }
            case "tituloDESC" -> {
                System.out.println("Se ordena por titulo DESC");
                lLibros.sort(Comparator.comparing(Libro::getTitulo).reversed());
            }
            default -> System.out.println("No se ordena");
        }
        
        System.out.println("Librería ordenada");
    }

}
