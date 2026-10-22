package ejemplosProcessBuilder;

import java.io.*;

public class PipeToSort {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd","/c","sort"); // en Linux ("sort")
        //pb.redirectOutput(new File("salidaPipeToSort.txt"));
        Process p = pb.start();
        
        // Escribimos líneas desordenadas al stdin del proceso sort
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
            w.write("zeta\n");
            w.write("alpha\n");
            w.write("beta\n");
            w.flush();
            //El método flush() obliga a que todos los datos que 
            //están almacenados en el búfer de un archivo se escriban inmediatamente
            //en el disco o en el archivo subyacente, garantizando que los datos se conserven sin demora, incluso antes de cerrar el archivo
           
            // cerramos la entrada para indicar EOF al proceso (es decir que hemos dejado de escribir en el fichero)
        }

        // Leemos la salida ordenada
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String s;
            while ((s = r.readLine()) != null) System.out.println("ORDENADO: " + s);
        }

        p.waitFor();
    }
    
    //Si pruebas el comando sort en el terminal, ejecuta sort y comienza a escribir para salir ejecuta Ctrl+Z
}
