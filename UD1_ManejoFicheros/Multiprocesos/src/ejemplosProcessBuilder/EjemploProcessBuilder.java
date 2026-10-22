package ejemplosProcessBuilder;

import java.io.*;
import java.util.Random;

public class EjemploProcessBuilder {
	
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd","/c","dir"); // en Linux: "ls", "-la"("cmd","/c","dir")
        pb.redirectErrorStream(true); // une stderr a stdout
        //Si queremos mostrar la salia en un fichero de texto ¿Qué tendrías que utilizar?
//       pb.redirectOutput(new File("salida.txt")); // o pb.redirectOutput(ProcessBuilder.Redirect.to(f));
        Process p = pb.start();
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println("SALIDA: " + line);
            }
        }
        Random r = new Random();          

        //Un valor de retorno de 0 significa éxito, indicando que el proceso o programa terminó sin errores, mientras
        //que un valor de retorno distinto de 0 (como 1) indica un fallo o error durante la ejecución del proceso
        //¿Qué debería devolver el programa?
        int exit = p.waitFor();
        System.out.println("Exit: " + exit);
    }
}
