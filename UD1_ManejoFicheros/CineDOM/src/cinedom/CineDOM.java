/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinedom;

import java.io.File;
import java.io.IOException;
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
public class CineDOM {

    private Document doc;

    public CineDOM(File archivo, File esquema) {
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

    // Método para cargar el esquema XSD
    private static Schema loadSchema(File esquema) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(esquema);
    }

    public void muestraDOM() {
        NodeList lista = doc.getElementsByTagName("pelicula");

        for (int i = 0; i < lista.getLength(); i++) {
            Element pelicula = (Element) lista.item(i);
            String sala = null;
            String titulo = "";
            String idioma = "";
            String calificacion = pelicula.getAttribute("calificacion");
            String precio = "";

            NodeList infoPelicula = pelicula.getChildNodes();

            for (int j = 0; j < infoPelicula.getLength(); j++) {
                Node info = infoPelicula.item(j);

                if (info.getNodeType() == Node.ELEMENT_NODE) {
                    switch (info.getNodeName()) {
                        case "titulo" -> {
                            titulo = info.getTextContent();
                        }
                        case "sala" -> {
                            sala = info.getTextContent();
                        }
                        case "idioma" -> {
                            idioma = info.getTextContent();
                        }
                        case "precio" -> {
                            precio = info.getTextContent();
                        }
                    }
                }
            }

            System.out.println("Titulo: " + titulo
                    + "\nSala: " + (sala == null ? "No hay sala asignada" : sala)
                    + "\nCalificacion: " + calificacion
                    + "\nIdioma: " + idioma
                    + "\nPrecio: " + precio + "€\n");

        }
    }

    public void muestraCarterleraIdioma(String idiomaFiltrado) {

        System.out.println("MUESTRO CARTELERA POR IDIOMA " + idiomaFiltrado);
        NodeList lista = doc.getElementsByTagName("pelicula");

        for (int i = 0; i < lista.getLength(); i++) {
            Element pelicula = (Element) lista.item(i);
            String sala = null;
            String titulo = "";
            String idioma = "";
            String calificacion = pelicula.getAttribute("calificacion");
            String precio = "";

            NodeList infoPelicula = pelicula.getChildNodes();

            for (int j = 0; j < infoPelicula.getLength(); j++) {
                Node info = infoPelicula.item(j);

                if (info.getNodeType() == Node.ELEMENT_NODE) {
                    switch (info.getNodeName()) {
                        case "titulo" -> {
                            titulo = info.getTextContent();
                        }
                        case "sala" -> {
                            sala = info.getTextContent();
                        }
                        case "idioma" -> {
                            idioma = info.getTextContent();
                        }
                        case "precio" -> {
                            precio = info.getTextContent();
                        }
                    }
                }
            }
            if (idiomaFiltrado == null || idiomaFiltrado.isBlank() || idioma.equalsIgnoreCase(idiomaFiltrado)) {
                System.out.println("Titulo: " + titulo
                        + "\nSala: " + (sala == null ? "No hay sala asignada" : sala)
                        + "\nCalificacion: " + calificacion
                        + "\nIdioma: " + idioma
                        + "\nPrecio: " + precio + "€\n");
            }

        }
    }

    public void mostrarCarteleraPorIdioma2(String idiomaFiltrado) {
        System.out.println("MUESTRO CARTELERA POR IDIOMA V2 " + idiomaFiltrado);
        NodeList idiomas = doc.getElementsByTagName("idioma");

        for (int i = 0; i < idiomas.getLength(); i++) {

            Element idioma = (Element) idiomas.item(i);

            if (idiomaFiltrado == null
                    || idiomaFiltrado.isEmpty()
                    || idioma.getTextContent().equalsIgnoreCase(idiomaFiltrado)) {
                mostrarPelicula((Element) idioma.getParentNode());
            }
        }

    }

    private void mostrarPelicula(Element pelicula) {
        String sala = null;
        String titulo = "";
        String idioma = "";
        String calificacion = pelicula.getAttribute("calificacion");
        String precio = "";

        NodeList infoPelicula = pelicula.getChildNodes();

        for (int j = 0; j < infoPelicula.getLength(); j++) {
            Node info = infoPelicula.item(j);

            if (info.getNodeType() == Node.ELEMENT_NODE) {
                switch (info.getNodeName()) {
                    case "titulo" -> {
                        titulo = info.getTextContent();
                    }
                    case "sala" -> {
                        sala = info.getTextContent();
                    }
                    case "idioma" -> {
                        idioma = info.getTextContent();
                    }
                    case "precio" -> {
                        precio = info.getTextContent();
                    }
                }
            }
        }

        System.out.println("Titulo: " + titulo
                + "\nSala: " + (sala == null ? "No hay sala asignada" : sala)
                + "\nCalificacion: " + calificacion
                + "\nIdioma: " + idioma
                + "\nPrecio: " + precio + "€\n");

    }

    public double averagePrice() {
        
        NodeList listaPrecios = doc.getElementsByTagName("precio");
        int totalPeliculas = listaPrecios.getLength();

        if (totalPeliculas == 0) {
            System.out.println("No hay películas para calcular el precio medio.");
            return 0;
        }

        double suma = 0;

        for (int i = 0; i < totalPeliculas; i++) {

            String precioTexto = listaPrecios.item(i).getTextContent().trim();
            suma += Double.parseDouble(precioTexto);
        }

        double media = suma / totalPeliculas;

        System.out.printf("Precio medio de las películas: %.2f\n" , media);

        return media;
    }
}
