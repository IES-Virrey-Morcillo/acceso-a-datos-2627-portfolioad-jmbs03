/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplosdom;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import javax.sound.sampled.AudioFormat;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author josema
 */
public class AccesoDOM {

    private Document doc;

    //Ejemplo 1
    public int abriXMLaDOM(File f) {

        try {
            System.out.println("Abriendo archivo XML file y generando DOM ....");
            //creamos nuevo objeto DocumentBuilder al que apunta la variable factory
            DocumentBuilderFactory factory
                    = DocumentBuilderFactory.newInstance();
            //ignorar comentarios y espacios blancos
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            factory.setNamespaceAware(true);
            factory.setValidating(false); // Usamos XSD, no DTD 
            factory.setSchema(loadSchema("Libros.xsd")); // Especificamos XSD
            //DocumentBuilder tiene el método parse que es el que genera DOM en memoria
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler() {

                @Override
                public void warning(org.xml.sax.SAXParseException e)
                        throws SAXException {
                    System.err.println("ADVERTENCIA: " + e.getMessage());
                }

                @Override
                public void error(org.xml.sax.SAXParseException e)
                        throws SAXException {
                    throw e;
                }

                @Override
                public void fatalError(org.xml.sax.SAXParseException e)
                        throws SAXException {
                    throw e;
                }
            });

            doc = builder.parse(f);
            // ahora doc apunta al arbol DOM y podemos recorrerlo
            System.out.println("DOM creado con éxito.");
            return 0;//si el método funciona
        } catch (ParserConfigurationException ex) {
            
            System.getLogger(AccesoDOM.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return -1;
        } catch (SAXException ex) {
            System.err.println("ERROR AL VALIDAR DOM");
            //System.getLogger(AccesoDOM.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return -2;
        } catch (IOException ex) {
            
            System.getLogger(AccesoDOM.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return -3;
        }

    }

    // Método para cargar el esquema XSD 
    private static Schema loadSchema(String schemaPath) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(new File(schemaPath));
    }

    // Ejemplo 2
    public void recorreDOMyMuestra() {

        String[] datos = new String[3];//lo usamos para la información de cada libro 
        String fecha, titulo, autor;
        fecha = titulo = autor = null;
        // Variable auxiliar con la que recorreré cada <Libro>.
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes(); //(1)Ver dibujo del árbo

        //recorrer el árbol DOM. El 1er nivel de nodos hijos del raíz
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);//node toma el valor de los hijos de raíz 
            //System.out.println("> Tipo elemento " + nodo.getNodeType() + nodo.getNodeName() + nodo.getNodeValue());
            fecha = titulo = autor = null;
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {//miramos nodos de tipo Element
                // Cada libro en ntemp
                Node ntemp = null;
                int contador = 1;
                //sacamos el valor del atributo publicado 
                //datos[0] = nodo.getAttributes().item(0).getNodeValue();
                fecha = nodo.getAttributes().getNamedItem("publicado").getNodeValue();
                //sacamos los valores de los hijos de nodo, Titulo y Autor
                NodeList nl2 = nodo.getChildNodes();//obtenemos lista de hijos (2)
                for (int j = 0; j < nl2.getLength(); j++) {//iteramos en esa lista
                    ntemp = nl2.item(j);
                    //System.out.println(">> Tipo elemento " + nodo.getNodeType() + nodo.getNodeName() + nodo.getNodeValue());
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                        //para conseguir el texto de titulo y autor, se 
                        //puedo hacer con getNodeValue(), también con //getTextContent() si es ELEMENT datos[contador]=ntemp.getTextContent() 
                        // también 
                        //datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                        //contador++;
                        if (ntemp.getNodeName().equals("Titulo")) {
                            titulo = ntemp.getTextContent();
                        } else if (ntemp.getNodeName().equals("Autor")) {
                            autor = ntemp.getTextContent();
                        }

                    }
                    //el array de String datos[] tiene los valores que necesitamos
                    //System.out.println(datos[0] + "--" + datos[2] + "--" + datos[1]);
                }//
                System.out.println(titulo + "--" + autor + "--" + fecha);
            }
        }
    }

    // Ejercicio extra: MUESTRA LOS LIBROS POSTERIORES A UN AÑO CONCRETO.
    public void librosPosterioresA(int anio) {

        String[] datos = new String[3];//lo usamos para la información de cada libro 
        String fecha, titulo, autor;
        fecha = titulo = autor = null;
        // Variable auxiliar con la que recorreré cada <Libro>.
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes(); //(1)Ver dibujo del árbo

        //recorrer el árbol DOM. El 1er nivel de nodos hijos del raíz
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);//node toma el valor de los hijos de raíz 
            //System.out.println("> Tipo elemento " + nodo.getNodeType() + nodo.getNodeName() + nodo.getNodeValue());
            fecha = titulo = autor = null;
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {//miramos nodos de tipo Element
                // Cada libro en ntemp
                Node ntemp = null;
                int contador = 1;
                //sacamos el valor del atributo publicado 
                //datos[0] = nodo.getAttributes().item(0).getNodeValue();
                fecha = nodo.getAttributes().getNamedItem("publicado").getNodeValue();
                int anioLibro;
                try {
                    anioLibro = Integer.parseInt(fecha);
                } catch (InputMismatchException e) {
                    anioLibro = 0;
                }
                if (anioLibro > anio) {
                    // Analizo el libro porque es posterior a la fecha de entrada.
                    //sacamos los valores de los hijos de nodo, Titulo y Autor
                    NodeList nl2 = nodo.getChildNodes();//obtenemos lista de hijos (2)
                    for (int j = 0; j < nl2.getLength(); j++) {//iteramos en esa lista
                        ntemp = nl2.item(j);
                        //System.out.println(">> Tipo elemento " + nodo.getNodeType() + nodo.getNodeName() + nodo.getNodeValue());
                        if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                            //para conseguir el texto de titulo y autor, se 
                            //puedo hacer con getNodeValue(), también con //getTextContent() si es ELEMENT datos[contador]=ntemp.getTextContent() 
                            // también 
                            //datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                            //contador++;
                            if (ntemp.getNodeName().equals("Titulo")) {
                                titulo = ntemp.getTextContent();
                            } else if (ntemp.getNodeName().equals("Autor")) {
                                autor = ntemp.getTextContent();
                            }

                        }
                        //el array de String datos[] tiene los valores que necesitamos
                        //System.out.println(datos[0] + "--" + datos[2] + "--" + datos[1]);
                    }//
                    System.out.println(titulo + "--" + autor + "--" + fecha);
                }
            }
        }
    }

    public int insertarLibroEnDOM(String titulo, String autor, String fecha) {
        try {
            System.out.println("Añadir libro al árbol DOM:" + titulo + ";" + autor + ";" + fecha);
            //crea los nodos=>los añade al padre desde las hojas a la raíz 
            //CREATE TITULO con el texto en medio 
            Node ntitulo = doc.createElement("Titulo");
            //crea etiquetas <Titulo>...</Titulo> 
            Node ntitulo_text = doc.createTextNode(titulo);
            //crea el nodo texto para el Titulo 
            ntitulo.appendChild(ntitulo_text);
            //añade el titulo a las etiquetas=><Titulo>titulo</Titulo>
            // Node nautor2=doc.createElement("Autor").appendChild(doc.createTextNode(autor));
            //one line doesn't work //CREA AUTOR
            Node nautor = doc.createElement("Autor");
            Node notro = doc.createElement("Otro");
            Node nautor_text = doc.createTextNode(autor);
            nautor.appendChild(nautor_text); //CREA LIBRO, con atributo y nodos Título y Autor

            Element nLibro = doc.createElement("Libro");
            nLibro.setAttribute("publicado", fecha);
            nLibro.appendChild(ntitulo);
            nLibro.appendChild(notro);
            nLibro.appendChild(nautor); //APPEND LIBRO TO THE ROOT
            //nLibro.appendChild(doc.createTextNode("\n"));//para insertar saltos de línea
            Node raiz = doc.getFirstChild();//tb. doc.getChildNodes().item(0)
            raiz.appendChild(nLibro);
            System.out.println("Libro insertado en DOM.");
            return 0;
        } catch (DOMException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int deleteNode(String tit) {
        System.out.println("Buscando el Libro " + tit + " para borrarlo");
        try {
            //Node root=doc.getFirstChild();
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("Titulo");
            Node n1;
            boolean encontrado = false;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);
                if (n1.getNodeType() == Node.ELEMENT_NODE) {//redundante por getElementsByTagName, no lo es lo mismo que si buscamos getChildNodes() 
                    // if (n1.getTextContent().equals(tit)) {
                    if (n1.getChildNodes().item(0).getNodeValue().equals(tit)) {
                        System.out.println("Borrando el nodo <Libro> con título " + tit);
                        //n1.getParentNode().removeChild(n1); //borra <Titulo> tit </Titulo>, pero deja Libro y Autor
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                        encontrado = true;
                        i--;
                        break;
                    }
                }
            }
            if (encontrado) {
                System.out.println("Libro borrado");
            } else {
                System.out.println("El libro " + tit + " no existe");
            }
            //Guardar el arbol DOM en un nuevo archivo para mantener nuestro archivo original
            //guardarDOMcomoArchivo("LibrosBorrados.xml");
            return 0;
        } catch (DOMException e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }

    // Borra todos los libros que se publican en un año en concreto.
    public int borrarPorAnio(int anio) {

        System.out.println("Buscando los libros publicados el " + anio);
        try {
            //Node root=doc.getFirstChild();
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("Libro");
            Node n1;
            int numBorrados = 0;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);

                System.out.println("ATT>" + n1.getAttributes().getNamedItem("publicado").getNodeValue());
                if (n1.getAttributes().getNamedItem("publicado").getNodeValue().equals(anio + "")) {
                    //System.out.println("Borrando el nodo <Libro> con título " + tit);

                    n1.getParentNode().removeChild(n1);

                    numBorrados++;
                    i--;
                }

            }
            if (numBorrados > 0) {
                System.out.println(numBorrados + " libros borrados");
            } else {
                System.out.println("No se han encontrado libros del año " + anio);
            }
            //Guardar el arbol DOM en un nuevo archivo para mantener nuestro archivo original
            //guardarDOMcomoArchivo("LibrosBorrados.xml");
            return 0;
        } catch (DOMException e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }

    }

    // Crea un nuevo archivo xml del DOM en memoria 
    public void guardarDOMcomoArchivo(String nuevoArchivo) {

        try {
            // Cargar esquema XSD para la validación
            Schema schema = loadSchema("Libros.xsd"); // Asegúrate de que la ruta del esquema sea correcta
            Validator validator = schema.newValidator();
            // Validar el DOM antes de guardarlo
            validator.validate(new DOMSource(doc));

            Source src = new DOMSource(doc); // Definimos el origen 
            StreamResult rst = new StreamResult(new File(nuevoArchivo)); // Definimos el resultado
            // Declaramos el Transformer que tiene el método .transform() que necesitamos.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            // Opción para indentar el archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con éxito\n");
        } catch (IllegalArgumentException | TransformerException e) {
            e.printStackTrace();
        } catch (SAXException ex) {
            System.getLogger(AccesoDOM.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(AccesoDOM.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    // Modifica el autor de un libro con el titulo que venga como parámetro.

    public void modificarAutor(String titulo, String autor) {
        try {
            System.out.println("Buscando Libro con el titulo '" + titulo + "' para modificar el autor por '" + autor + "'...");

            NodeList nlista = doc.getElementsByTagName("Libro");
            boolean encontrado = false;

            for (int i = 0; i < nlista.getLength(); i++) {
                Node nodoActual = nlista.item(i);

                if (nodoActual.getNodeType() == Node.ELEMENT_NODE) {

                    Element elementoLibro = (Element) nodoActual;

                    String tituloActual = elementoLibro.getElementsByTagName("Titulo").item(0).getTextContent();

                    if (tituloActual.equals(titulo)) {

                        Node nodoAutor = elementoLibro.getElementsByTagName("Autor").item(0);

                        nodoAutor.setTextContent(autor);

                        System.out.println("¡Exito! El nuevo autor de '" + titulo + "' es " + autor);
                        encontrado = true;

                        break;
                    }
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró ningún libro con el título: " + titulo);
            }

        } catch (DOMException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    // Modifica el autor de un libro con el titulo que venga como parámetro.
    public void modificarAutorV2(String titulo, String autor) {
        try {
            System.out.println("Buscando Libro con el titulo '" + titulo + "' para modificar el autor por '" + autor + "'...");

            NodeList nlista = doc.getElementsByTagName("Libro");
            boolean encontrado = false;

            for (int i = 0; i < nlista.getLength(); i++) {
                Node nodoActual = nlista.item(i);
                NodeList hijosDeLibro = nodoActual.getChildNodes();
                Element eTitulo = null, eAutor = null;

                for (int j = 0; j < hijosDeLibro.getLength(); j++) {

                    Node nodoHijo = hijosDeLibro.item(j);

                    if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                        if (nodoHijo.getNodeName().equals("Titulo")) {
                            eTitulo = (Element) nodoHijo;
                        } else if (nodoHijo.getNodeName().equals("Autor")) {
                            eAutor = (Element) nodoHijo;

                        }
                    }
                }

                if (eTitulo.getTextContent().equals(titulo)) {

                    eAutor.setTextContent(autor);

                    System.out.println("¡Exito! El nuevo autor de '" + titulo + "' es " + autor);
                    encontrado = true;

                    break;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró ningún libro con el título: " + titulo);
            }

        } catch (DOMException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
