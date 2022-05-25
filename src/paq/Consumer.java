package paq;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Consumer extends Thread {
    Buffer buffer;
    int ID;
    int r1;
    int r2;
    int E;
    public DefaultTableModel tabla2;
    
    Consumer(Buffer buffer, int ID, int E,  DefaultTableModel tabla2) {
        this.buffer = buffer;
        this.ID=ID;
        this.E=E;
        this.tabla2 = tabla2;
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        String product;
        
        for(int i=0 ;  ; i++) {
            product = this.buffer.consume();
            
            String res = opRes (product.charAt(0), Character.getNumericValue(product.charAt(2)), Character.getNumericValue(product.charAt(4)));
            
            String sf1 = String.format("Consumer %s consumed: %s ",ID, product);  
            
            String sf2 = String.format("Result: %s ",res);
            
            Object[] row = {product, res};
            this.tabla2.addRow(row);
            
            try {
                Thread.sleep(E);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.buffer.print(sf1);
            this.buffer.print(sf2);
            ID++;
        }
    }
    
    private String opRes(char op, int a, int b) {
        String res = "undefined";
            switch (op) {
                case '+' : res = Integer.toString(a+b); break;
                case '-' : res = Integer.toString(a-b); break;
                case '*' : res = Integer.toString(a*b); break;
                case '/' : 
                    if (b != 0) {
                        if (a % b == 0) res = Integer.toString(a/b);
                        else {
                            for (int i = 1; i < 10; i++){
                                if (a % i == 0 && b % i == 0){
                                    res = Integer.toString(a/i) + '/' + Integer.toString(b/i);
                                }
                            }
                        }
                    }
                    else res = "undefined";
                    break;
            }
        return res;
    }
    
}
