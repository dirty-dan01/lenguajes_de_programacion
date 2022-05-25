package paq;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;


public class Producer extends Thread {
    Buffer buffer;
    int ID;
    int r1;
    int r2;
    int E;
    
    Producer(Buffer buffer, int ID, int r1, int r2, int E) {
        this.buffer = buffer;
        this.ID = ID;
        this.r1=r1;
        this.r2=r2;
        this.E=E;
    }
    
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        
        String product;
        
        String operators = "+-*/";
        String nums = "0123456789";
        
        // Generador de números aleatorios aislado del hilo actual
        ThreadLocalRandom random = ThreadLocalRandom.current();
        
        for(int i=0 ;  ; i++) {
            
            // Generamos de manera aleatoria un operador y lo almacenamos en op
            char op = operators.charAt(random.nextInt(4));
        
            // Generamos de manera aleatoria un numero entre el intervalo de r1 y r2
            char x1 = nums.charAt(random.nextInt(Math.min(this.r1, this.r2),Math.max(this.r1, this.r2)));
            char x2 = nums.charAt(random.nextInt(Math.min(this.r1, this.r2),Math.max(this.r1, this.r2)));
            
            // Guardamos la operacion completa en un string
            String res = op + " " + x1 + " " + x2;
            
            product = res;
            this.buffer.produce(product);        
            String sf1 =String.format("Producer %s produced: %s ",ID, product);  
            this.buffer.print(sf1);
            ID++;
            
            //String row = this.buffer.opRes(product);
            //this.tabla1.addRow(new object[]{ID, row});
            
            try {
                Thread.sleep(E);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
