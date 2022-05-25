package paq;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Buffer {
    
    boolean flag=false;
    private int MAX_SIZE;
    public final ArrayList<String> operaciones;
    public DefaultTableModel tabla1;
    public DefaultTableModel tabla2;
    
    Buffer(int max, DefaultTableModel tabla1, DefaultTableModel tabla2) {
        operaciones = new ArrayList<>();
        this.MAX_SIZE = max;
        this.tabla1 = tabla1;
        this.tabla2 = tabla2;
    }
     
    synchronized String consume() {
       notifyAll();
       
        while(operaciones.isEmpty()) {
            try {
                System.out.println("esperando  al buffer");
                wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String data = this.operaciones.get(0);
        operaciones.remove(data);
        
        return data;
    }
    
    synchronized void produce(String product) {
                
        while(operaciones.size() == MAX_SIZE) {
            try {
                System.out.println("productor lleno");               
                wait();
                
            } catch (InterruptedException ex) {               
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.operaciones.add(product);        
                              
        Object[] row = {product};
        this.tabla1.addRow(row);
        
        notifyAll();
    }
    
    synchronized void print(String string) { 
        System.out.println(string);
        notifyAll();
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
