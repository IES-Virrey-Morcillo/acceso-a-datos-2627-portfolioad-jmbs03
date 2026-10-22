package ejemplosProcessBuilder;
import java.io.*;

public class ejRuntime {
    public static void main(String[] args) throws Exception {
    	
    	//Utilizamos el objeto Runtime, el método exec se encarga de ejecutar el comando que se indique, devuelve
    	// un proceso (objeto Proccess)
        Process p = Runtime.getRuntime().exec(new String[]{"cmd","/c","dir"}); // Windows
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
        	//Se lee lo que devuelve el comando
            String l;
            while ((l = r.readLine()) != null) System.out.println(l);
        }
        //Espera a que termine el proceso de ejecutarse
        p.waitFor();
    }
}
