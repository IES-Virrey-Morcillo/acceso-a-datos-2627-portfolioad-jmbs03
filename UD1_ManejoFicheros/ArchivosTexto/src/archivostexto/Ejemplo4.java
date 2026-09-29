package archivostexto;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josema
 */
public class Ejemplo4 {

    public static void main(String[] args) {
    
        System.out.println("LECTURA/ESCRITURA ARCHIVOS BINARIOS");
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("persona.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Persona p = new Persona("Alejandro",22,1.76);
            Persona p2 = new Persona("Denis", 20,1.80);
            Persona p3 = new Persona("Antonio", 25,1.80);
            oos.writeObject(p);
            oos.writeObject(p2);
            oos.writeObject(p3);
            oos.close();
            
            FileInputStream fis = new FileInputStream("persona.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

//            Persona ps1 = (Persona) ois.readObject();
//            Persona ps2 = (Persona) ois.readObject();           
//            Persona ps3 = (Persona) ois.readObject();           
//            System.out.println(ps1);
//            System.out.println(ps2);
//            System.out.println(ps3);
            
   
            while (true) {
                
                try {
                    Persona paux = (Persona) ois.readObject();
                    System.out.println(paux);
                   
                } catch (EOFException e) {
                    System.out.println("Fin de fichero");
                    break;
                }
            }
            
            
            
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error escritura");
        } catch (IOException ex) {
            Logger.getLogger(Ejemplo4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejemplo4.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejemplo4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
                
    }

}
