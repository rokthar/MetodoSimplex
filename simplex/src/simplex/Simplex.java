package simplex;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Simplex {
    public static ArrayList<funciones> Listfunciones = new ArrayList();
    
    public static void main(String[] args) {
        int numVariables = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el numero de variables"));
        int tipo = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce:\n"
                + "1 si es MAX\n"
                + "2 si es MIN"));
        
        //LLENADO DE LA FUNCION OBJETIVO
        funciones funcionZ = new funciones();
        for (int i = 0; i < numVariables; i++) {
            funcionZ.incognitas.add(Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor de X"+(i+1))));
        }
        Listfunciones.add(funcionZ);

        
        //LLENADO DE LAS RESTRICCIONES
        int numRest = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el numero de restricciones"));
        for (int i = 0; i < numRest; i++) {
            funciones restriccion = new funciones();
            for (int j = 0; j < numVariables; j++) {
                restriccion.incognitas.add(Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor de X"+(j+1))));
            }
            restriccion.condicion = JOptionPane.showOptionDialog(null, "Seleccione el operador",
                            "Ingresar funcion", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, 
                            new Object[]{"≤", "=", "≥"}, "≥");
            restriccion.terminoIndependiente = Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor independiente"));
            Listfunciones.add(restriccion);
        }
        
        System.out.println("Funciones Originales\n");
 
        Presentar(); //PRESENTAR 
        
        VerificarMaxMin(tipo); //VERIFICAR MAX Y MIN EN LA FUNC. OBJ
       
        verificarRestricciones(numRest);
              
        System.out.println("\nTabla SIMPLEX\n");
        presentarTabla();
       
    }
    
    public static void Presentar(){
       //RECORRIDO DE LAS RESTRICCIONES       
        for (int i = 0; i < Listfunciones.size(); i++) {
            String cadr ="";
            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                cadr += Listfunciones.get(i).incognitas.get(j)+"x"+(j+1)+" ";
            }
            if(i == 0){
                
            }else{
            switch (Listfunciones.get(i).condicion) {
                case 0:
                    cadr += " <= ";
                    break;
                case 1:
                    cadr += " = ";
                    break;
                case 2:
                    cadr += " > ";
                    break;
                default:
                    break;
            }
            cadr += Listfunciones.get(i).terminoIndependiente;
            }
            System.out.println(cadr);
        }
    }
    
    public static void VerificarMaxMin(int tipo){
        //VERIFICAR MAX=1 Y MIN=2
       if (tipo == 1){
           for (int i = 0; i < Listfunciones.get(0).incognitas.size(); i++) {
               Listfunciones.get(0).incognitas.set(i, Listfunciones.get(0).incognitas.get(i) * -1);
           }
       }
       Listfunciones.get(0).terminoIndependiente=0.0;
       Listfunciones.get(0).condicion=1;
    }
    
    public static void verificarRestricciones(int numRest){
        for (int i = 1; i < Listfunciones.size(); i++) {
            
                switch (Listfunciones.get(i).condicion) {
                case 0:                   
                     Listfunciones.get(i).condicion = 1; //valor de igualdad
                    // agrega variable de holgura
                    Listfunciones.get(i).incognitas.add(1.0);
                    for (int j = 0; j < Listfunciones.size(); j++) { // agregar incognita en las demas ecuaciones
                        if (j != i) { // que no sea la misma ecuacion...
                            Listfunciones.get(j).incognitas.add(0.0);
                        }
                    }
                    break;
                case 2:
                    
                    Listfunciones.get(i).condicion = 1; //valor de igualdad
                    // agrega variable de holgura
                    Listfunciones.get(i).incognitas.add(1.0);
                    for (int j = 0; j < Listfunciones.size(); j++) { // agregar incognita en las demas ecuaciones
                        if (j != i) { // que no sea la misma ecuacion...
                            Listfunciones.get(j).incognitas.add(0.0);
                        }
                    }
                    break;
                default:
                    break;
            }
            
        }
    }
    
    
    public static void presentarTabla(){
        //RECORRIDO DE LAS RESTRICCIONES       
        for (int i = 0; i < Listfunciones.size(); i++) {
            String cadr ="";
            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                cadr += Listfunciones.get(i).incognitas.get(j)+"|";
            }
            cadr += Listfunciones.get(i).terminoIndependiente+"|";
            System.out.println(cadr);
        }
    }
    
}
