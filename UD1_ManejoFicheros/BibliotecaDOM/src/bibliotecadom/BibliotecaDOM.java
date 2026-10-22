/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bibliotecadom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author josema
 */
public class BibliotecaDOM {

    Document doc;

    public BibliotecaDOM(File archivo, File esquema) {
        try {
            //Abrimos en memoria el DOM del XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Quitamos comentarios y espacios en blanco
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setNamespaceAware(true);
            factory.setValidating(false); // Usamos XSD, no DTD
            factory.setSchema(loadSchema(esquema)); // Especificamos XSD
            //Genera DOM en memoria
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Establecer el manejador de errores
            builder.setErrorHandler(new DefaultHandler() {
                @Override
                public void error(SAXParseException e) throws SAXException {
                    System.err.println("Error de validación: " + e.getMessage());
                    throw e;  // Detener la ejecución en caso de error
                }

                @Override
                public void fatalError(SAXParseException e) throws SAXException {
                    System.err.println("Error fatal: " + e.getMessage());
                    throw e;  // Detener la ejecución en caso de error fatal
                }

                @Override
                public void warning(SAXParseException e) throws SAXException {
                    System.out.println("Advertencia: " + e.getMessage());
                }
            });

            doc = builder.parse(archivo);

            //Si no hay fallo
            System.out.println("DOM creado con exito.");
        } catch (SAXException e) {
            System.out.println("El archivo no cumple el esquema");
        } catch (IOException | ParserConfigurationException e) {
            System.err.println("No se ha podido leer el archivo");
            //this.doc = null;
        }
    }

    // Método para cargar el esquema XSD
    private static Schema loadSchema(File esquema) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(esquema);
    }

    public void guardar(File archivo, File esquema) throws TransformerConfigurationException, TransformerException {
        try {
            // Cargar esquema XSD para la validación
            Schema schema = loadSchema(esquema); // Asegúrate de que la ruta del esquema sea correcta
            Validator validator = schema.newValidator();
            // Validar el DOM antes de guardarlo
            try {
                validator.validate(new DOMSource(doc));
                System.out.println("El DOM es válido según el esquema XSD.");
                // Definir el origen y el resultado para la transformación
                Source src = new DOMSource(doc);
                StreamResult rst = new StreamResult(archivo);
                // Crear Transformer para convertir el DOM a un archivo XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();

                // Opciones de formato del archivo XML
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indentamount", "4");
                // Guardar el archivo XML
                transformer.transform(src, rst);
                System.out.println("Archivo creado del DOM con éxito\n");
            } catch (IOException | IllegalArgumentException | TransformerException
                    | SAXException e) {
                System.out.println("Error de validación: " + e.getMessage());
            }
        } catch (SAXException e) {
            System.out.println("El documento modificado no cumple el esquema");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filtro podrá tener los valores: Nulo o cadena vacía libro pelicula album
     */
    public void mostrarBiblioteca(String filtro) {

        Element libro, pelicula, albumMusica;

        Node raiz = doc.getFirstChild();
        NodeList canciones = doc.getElementsByTagName("cancion");
        NodeList hijos = raiz.getChildNodes();

        for (int i = 0; i < hijos.getLength(); i++) {

            Node nodo = hijos.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoActual = (Element) nodo;

                if (filtro.isEmpty() || filtro.equalsIgnoreCase("todos") || nodo.getNodeName().equalsIgnoreCase(filtro)) {

                    switch (nodo.getNodeName()) {

                        case "libro" -> {
                            mostrarLibro(elementoActual);
                            break;

                        }
                        case "pelicula" -> {
                            mostrarPeliculaV2(elementoActual);
                            break;

                        }
                        case "albumMusica" -> {
                            mostrarAlbum(elementoActual);
                            break;

                        }

                    }

                }

            }

        }

    }

    public int insertarLibro(String titulo, String editorial, String fechaPublicacion, ArrayList<String> autores) {

        if (autores.isEmpty()) {
            System.out.println("No se puede crear un libro sin autores");
            return -2;
        } else if (autores.size() > 3) {
            System.out.println("No se puede crear un libro con más de 3 autores");
            return -3;
        }

        try {
            Element raiz = doc.getDocumentElement();

            Element libroNuevo = doc.createElement("libro");
            libroNuevo.setAttribute("fechaPublicacion", fechaPublicacion);

            Element nodoTitulo = doc.createElement("titulo");
            nodoTitulo.setTextContent(titulo);

            Element nodoEditorial = doc.createElement("editorial");
            nodoEditorial.setTextContent(editorial);

            libroNuevo.appendChild(nodoTitulo);
            libroNuevo.appendChild(nodoEditorial);

            for (int i = 0; i < autores.size(); i++) {

                Element nodoAutor = doc.createElement("autor");
                nodoAutor.setTextContent(autores.get(i));
                libroNuevo.appendChild(nodoAutor);

            }

            raiz.appendChild(libroNuevo);

        } catch (DOMException e) {
            System.out.println("Error al insertar un libro");
            return -1;
        }
        return 0;
    }

    /**
     * Mathias
     * @param titulo
     * @param productora
     * @return 
     */
    public int modificarProductoraPelicula(String titulo, String productora) {
        if (doc == null || titulo == null || productora == null) {
            System.out.println("Llamada incorrecta");
            return -1;
        }

        int modificadas = 0;
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            if (tituloDe(pelicula).equalsIgnoreCase(titulo.trim())) {
                // productora es hijo unico de pelicula
                Node contenidoProductora =  pelicula.getElementsByTagName("productora").item(0);
                
                if (contenidoProductora.getTextContent().equalsIgnoreCase(productora)) {
                    System.out.println("No se modifica nada");
                    return 2;
                } else {
                    System.out.println("Modificado OK");
                    contenidoProductora.setTextContent(productora);
                    return 0;
                }

            }
        }
        // No encuentro la película con ese título tras iterar toda la lista.
        System.out.println("No se encuentra la pelicula " + titulo);
        return 1;
    }

    // Devuelve el texto del <titulo> de un item
    private String tituloDe(Element item) {
        NodeList t = item.getElementsByTagName("titulo");
        return t.getLength() > 0 ? t.item(0).getTextContent().trim() : "";
    }

    public int eliminarItem(String titulo) {
        
        Node raiz = doc.getFirstChild();
        NodeList hijos = raiz.getChildNodes();
        
        for (int i = 0; i < hijos.getLength(); i++) {
            Node nodo = hijos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                String tituloItem = elemento.getElementsByTagName("titulo").item(0).getTextContent();

                if (tituloItem.equalsIgnoreCase(titulo)) {

                    raiz.removeChild(nodo);
                    System.out.println("Item borrado");
                    return 0;
                }
            }
        }
        System.out.println("No se encontro la pelicula ");
        return -1;

    }

    private void mostrarLibro(Element libro) {

        String titulo, editorial, fechaPublicacion;
        ArrayList<String> autores = new ArrayList();

        titulo = libro.getElementsByTagName("titulo").item(0).getTextContent();
        editorial = libro.getElementsByTagName("editorial").item(0).getTextContent();
        fechaPublicacion = libro.getAttribute("fechaPublicacion");
        
        System.out.print("Titulo: " + titulo + " Editorial: " + editorial + " Fecha: " + fechaPublicacion);
        NodeList nlAutores = libro.getElementsByTagName("autor");
        for (int i = 0; i < nlAutores.getLength(); i++) {
            System.out.print(" Autor: " + nlAutores.item(i).getTextContent());
        }
        System.out.println("");
    }

    private void mostrarAlbum(Element albumMusica) {

        String fechaP, titulo, autor, discografica;
        ArrayList<String> canciones = new ArrayList();

        titulo = albumMusica.getElementsByTagName("titulo").item(0).getTextContent();
        autor = albumMusica.getElementsByTagName("autor").item(0).getTextContent();
        discografica = albumMusica.getElementsByTagName("discografica").item(0).getTextContent();
        fechaP = albumMusica.getAttribute("fechaPublicacion");

        System.out.println("Titulo: " + titulo + "Autor: " + autor + "Discografia: " + discografica + "Fecha publicacion: " + fechaP);
        NodeList nlcanciones = albumMusica.getElementsByTagName("cancion");

        for (int i = 0; i < nlcanciones.getLength(); i++) {

            System.out.println("Cancion: " + nlcanciones.item(i).getTextContent());

        }

    }

    private void mostrarPelicula(Element pelicula) {

        String titulo, productora, fechaP;
        ArrayList<String> actor = new ArrayList();

        titulo = pelicula.getElementsByTagName("titulo").item(0).getTextContent();
        productora = pelicula.getElementsByTagName("productora").item(0).getTextContent();
        fechaP = pelicula.getAttribute("fechaPublicacion");

        System.out.println("Titulo: " + titulo + "Productora: " + productora + "Fecha publicacion: " + fechaP);
        NodeList nlactores = pelicula.getElementsByTagName("actor");

        for (int i = 0; i < nlactores.getLength(); i++) {

            System.out.println("Actor: " + nlactores.item(i).getTextContent());

        }

    }
    
    private void mostrarPeliculaV2(Element pelicula) {

        String titulo,productora,fechaP;
        ArrayList<String> actores = new ArrayList();

        NodeList hijos = pelicula.getChildNodes();
        fechaP = pelicula.getAttribute("fechaPublicacion");
        titulo=productora="";
        
        for(int i=0;i<hijos.getLength();i++) {
            Node nodo = hijos.item(i);
            
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                switch(nodo.getNodeName()) {
                    case "titulo" -> {
                        titulo = nodo.getTextContent();
                    }
                    case "productora" -> {
                        productora = nodo.getTextContent();
                    }
                    case "actor" -> {
                        actores.add(nodo.getTextContent());
                    }
                }
            }
        }

        System.out.println("Titulo: " + titulo + " Productora: " + productora + " Fecha publicacion: " + fechaP);

        for (int i = 0; i < actores.size(); i++) {

            System.out.println(">Actor: " + actores.get(i));

        }

    }
    
}
