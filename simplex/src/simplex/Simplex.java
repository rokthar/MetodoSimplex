package simplex;

import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;

public class Simplex {
    public static ArrayList<funciones> Listfunciones = new ArrayList();
    public static ArrayList<Double> terminoPivot = new ArrayList();
    public static void main(String[] args) {
        int numVariables = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el numero de variables"));
        int tipo = JOptionPane.showOptionDialog(null, "Seleccione el operador",
                            "Ingresar funcion", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, 
                            new Object[]{"MAX", "MIN"}, "MAX");
        
        //LLENADO DE LA FUNCION OBJETIVO
        funciones funcionZ = new funciones();
        JOptionPane.showMessageDialog(null, "Ingrese la Función Objetivo");
        for (int i = 0; i < numVariables; i++) {
            funcionZ.incognitas.add(Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor de X"+(i+1))));
        }
        funcionZ.terminoIndependiente = (Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor del termino Independiente")));
        Listfunciones.add(funcionZ);

        
        //LLENADO DE LAS RESTRICCIONES
        int numRest = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el numero de restricciones"));
        for (int i = 0; i < numRest; i++) {
            funciones restriccion = new funciones();
            JOptionPane.showMessageDialog(null, "Ingrese la Ecuación "+(i+1));
            for (int j = 0; j < numVariables; j++) {
                restriccion.incognitas.add(Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor de X"+(j+1))));
            }
            restriccion.condicion = JOptionPane.showOptionDialog(null, "Seleccione el operador",
                            "Ingresar funcion", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, 
                            new Object[]{"<=", "=", ">="}, "<=");
            restriccion.terminoIndependiente = Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce el valor independiente"));
            Listfunciones.add(restriccion);
        }
        
        System.out.println("Funciones Originales\n");
 
        Presentar(); //PRESENTAR 
        
        VerificarMaxMin(tipo); //VERIFICAR MAX Y MIN EN LA FUNC. OBJ
       
        verificarRestricciones(numRest);
              
        System.out.println("\nTabla SIMPLEX\n");
        presentarTabla();
        
        pivotear();
       
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
       if (tipo == 0){
           for (int i = 0; i < Listfunciones.get(0).incognitas.size(); i++) {
               Listfunciones.get(0).incognitas.set(i, Listfunciones.get(0).incognitas.get(i) * -1);
           }
       }
       //Listfunciones.get(0).terminoIndependiente=0.0;
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
                    Listfunciones.get(i).incognitas.add(-1.0);
                    for (int j = 0; j < Listfunciones.size(); j++) { // agregar incognita en las demas ecuaciones
                        if (j != i) { // que no sea la misma ecuacion...
                            Listfunciones.get(j).incognitas.add(0.0);
                        }
                    }
                    for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                        Listfunciones.get(i).incognitas.set(j, Listfunciones.get(i).incognitas.get(j)* -1);
                    }
                    Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * -1; 
                    break;
                default:
                    break;
            }
            
        }
    }
    
    
    public static void presentarTabla(){
        String cad="";
        for (int i = 0; i < Listfunciones.get(0).incognitas.size(); i++) {
            cad += "\t X"+(i+1)+" \t|";
        }
        cad += "\t B \t|";
        System.out.println(cad); 
       //RECORRIDO DE LAS RESTRICCIONES       
        for (int i = 1; i < Listfunciones.size(); i++) {
            String cadr ="";
            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                Double num = Math.round(Listfunciones.get(i).incognitas.get(j) * 100.0)/100.0;
                cadr +="\t"+ num+" \t|";
            }
            Double num = Math.round(Listfunciones.get(i).terminoIndependiente * 100.0)/100.0;
            cadr += "\t"+num+" \t|";
            System.out.println(cadr);
        }
        cad ="";
        for (int i = 0; i < Listfunciones.get(0).incognitas.size(); i++) {
            Double num = Math.round(Listfunciones.get(0).incognitas.get(i) * 100.0)/100.0;
            cad += "\t"+ num +" \t|";
        }
        Double num = Math.round(Listfunciones.get(0).terminoIndependiente * 100.0)/100.0;
            cad += "\t"+num+" \t|";
        System.out.println(cad);
    }
    
   
    
    public static void pivotear(){
        terminoPivot.clear();
        Double a=0d; //encontrar pivote
        Double d=0d; //obtener razon
        int pivote = 0; //indice del pivote
        Double div=0d; //division
        int func = 0; //ecuación pivote
        for (int i = 0; i < Listfunciones.get(0).incognitas.size(); i++) {
            if(Listfunciones.get(0).incognitas.get(i) < a){
                a = Listfunciones.get(0).incognitas.get(i);
                pivote = i;
            }
        }
        
        if(a != 0){
        System.out.println("Termino Pivote: "+a+" indice pivote: X"+(pivote+1));
        a=Double.MAX_VALUE;
        //ENCONTRAR RAZON
            for (int i = 1; i < Listfunciones.size(); i++) {
                if(Listfunciones.get(i).incognitas.get(pivote) >= 0){
                    div = Listfunciones.get(i).terminoIndependiente/Listfunciones.get(i).incognitas.get(pivote);
                    if(div < a){
                        a = div;
                        func = i;
                    }
                }
            }
            
            System.out.println("razon de la ecuacion "+func+" es: "+a);
            if(func != 0){
                //MULTIPLICAR Y ELIMINAR
                for (int i = 0; i < Listfunciones.size(); i++) {
                    Double terminoPivo = Listfunciones.get(i).incognitas.get(pivote);
                    if(terminoPivo != 0){
                            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)/Math.abs(terminoPivo));
                            }
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente / Math.abs(terminoPivo);
                            terminoPivot.add(Math.abs(terminoPivo));
                    }else{
                        terminoPivot.add(1.0);
                    }  
                }

                multiplicar(func, pivote);
                //pivotear2();
            }else{
                System.out.println("Programa Terminado - Los valores de las Restricciones son Negativos");
            }
        }else{
            System.out.println("Programa Termino");
        }
    
    }
    
    public static void multiplicar(int func, int pivote){
        for (int i = 0; i < Listfunciones.size(); i++) {            
            if(Listfunciones.get(i).incognitas.get(pivote) != 0){
                if(Listfunciones.get(i).incognitas.get(pivote) > 0){
                    if(Listfunciones.get(func).incognitas.get(pivote) > 0){
                        if(i != func){
                            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j) * -1));
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
                            }
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente * -1) ;
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
                        }
                    }else{
                        if(i != func){
                            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j)));
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
                            }
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente) ;
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
                        }
                    }
                }else{
                     if(Listfunciones.get(func).incognitas.get(pivote) > 0){
                         if(i != func){
                            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j)));
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
                            }
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente) ;
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
                        }
                     }else{
                         if(i != func){
                            for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j) * -1));
                                Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
                            }
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente * -1) ;
                            Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
                        }
                     }
                }
//                if(Listfunciones.get(i).incognitas.get(pivote) > 0){
//                    if(i != func){
//                        for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
//                            Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j) * -1));
//                            Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
//                        }
//                        Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente * -1) ;
//                        Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
//                    }
//                }else{
//                    if(i != func){
//                        for (int j = 0; j < Listfunciones.get(i).incognitas.size(); j++) {
//                            Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)+(Listfunciones.get(func).incognitas.get(j)));
//                            Listfunciones.get(i).incognitas.set(j,Listfunciones.get(i).incognitas.get(j)* terminoPivot.get(i));
//                        }
//                        Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente + (Listfunciones.get(func).terminoIndependiente) ;
//                        Listfunciones.get(i).terminoIndependiente = Listfunciones.get(i).terminoIndependiente * terminoPivot.get(i);
//                    }
//                }
            }
        }
        presentarTabla();
        pivotear();
    }
}
