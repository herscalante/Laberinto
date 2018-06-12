package laberinto;

import arbol.Arbol;
import arbol.NodoArbol;
import datos.InfoNodoActual;
import lista.Lista;
import java.util.Random;

/**
 *
 * @author Jeremias Reyes, Leandro Navarrete, Hernan Escalante
 */
//Clase Laberinto para creacion , y la solucion de laberintos cuadrados
public class Laberinto {

    //Atributos
    private int tam;
    private NodoArbol[][] matrizDeNodos;
    private Arbol arbol;

    //Constructor para declarar la matriz de nodos y tamaño del laberinto con un tamaño especifico
    public Laberinto(int tam) {
        this.tam = tam;
        this.matrizDeNodos = new NodoArbol[tam][tam];
        crearNodos();
        this.arbol = new Arbol(matrizDeNodos[0][0]);
    }

    //Constructor para declarar la matriz de nodos con un tamaño de 10X10
    public Laberinto() {
        this.tam = 10;
        this.matrizDeNodos = new NodoArbol[10][10];
        crearNodos();
        this.arbol = new Arbol(matrizDeNodos[0][0]);
    }

    //Metodo para inicializar los nodos del arbol
    private void crearNodos() {
        for (int i = 0; i < matrizDeNodos.length; i++) {
            for (int j = 0; j < matrizDeNodos[0].length; j++) {
                matrizDeNodos[i][j] = new NodoArbol();
            }
        }
    }

    //Metodo recursivo que establece los nodos y a sus enlaces directos como no visitados si no son null
    public void noVisitados(NodoArbol actual) {
        if (actual != null) {
            actual.setVisitado(false);
            noVisitados(actual.getEnlaceAbajo());
            noVisitados(actual.getEnlaceArriba());
            noVisitados(actual.getEnlaceIzquierdo());
            noVisitados(actual.getEnlaceDerecho());
        }
    }

    //Metodo que nos retorna una Lista con la solucion del laberinto  (Solo para ver que el laberinto genere bien la solucion) 
    public Lista listaSoluciones(NodoArbol actual) {
        noVisitados(actual);
        Lista listaDeSoluciones = new Lista();                                    //Lista de soluciones
        actual.setVisitado(true);
        while (actual != arbol.getHojaFin()) {                                      //Mientras no sea el final del arbol
            if (actual.getEnlaceAbajo() != null) {
                if (actual.getEnlaceAbajo().isVisitado()) {
                } else {
                    listaDeSoluciones.insertarAlFinalDeLista(actual);
                    actual = actual.getEnlaceAbajo();
                    actual.setVisitado(true);
                    continue;
                }
            }
            if (actual.getEnlaceArriba() != null) {
                if (actual.getEnlaceArriba().isVisitado()) {
                } else {
                    listaDeSoluciones.insertarAlFinalDeLista(actual);
                    actual = actual.getEnlaceArriba();
                    actual.setVisitado(true);
                    continue;
                }
            }
            if (actual.getEnlaceDerecho() != null) {
                if (actual.getEnlaceDerecho().isVisitado()) {
                } else {
                    listaDeSoluciones.insertarAlFinalDeLista(actual);
                    actual = actual.getEnlaceDerecho();
                    actual.setVisitado(true);
                    continue;
                }
            }
            if (actual.getEnlaceIzquierdo() != null) {
                if (actual.getEnlaceIzquierdo().isVisitado()) {
                } else {
                    listaDeSoluciones.insertarAlFinalDeLista(actual);
                    actual = actual.getEnlaceIzquierdo();
                    actual.setVisitado(true);
                    continue;
                }
            } else {
                listaDeSoluciones.eliminarFinalDeLista();
                actual = actual.getAnterior();
            }
        }

        listaDeSoluciones.insertarAlFinalDeLista(matrizDeNodos[tam - 1][tam - 1]);
       
        return listaDeSoluciones;
    }

    
    
    //Metodo que establece los datos principales de los NodosArbol para la generacion del laberinto.
    public InfoNodoActual datos(NodoArbol actual) {
        Random rnd = new Random();
        InfoNodoActual datos = new InfoNodoActual();

        //Establecemos los cuatro puntos principales para generar el laberinto
        if (actual == matrizDeNodos[0][0]) {
            datos.setTipoNodo("nodoSalida");                                    //Nodo salida                   
            datos.setSiguienteNodo(rnd.nextInt(2) + 2);                         //Hace referencia a los enlace der, abajo
            datos.setFila(0);                                                   //Establecemos la fila
            datos.setColumna(0);                                                //Establecemos la columna
            return datos;                                                       //Devolvemos los datos obtenidos

        } else if (actual == matrizDeNodos[0][tam - 1]) {                       
            int num = rnd.nextInt(2);                                           //Se genera un numero aleatorio entre 0 y 1
            if (num == 0) {                                                     //Hace referencia al Enlace izquierdo    
                datos.setSiguienteNodo(0);
            }
            if (num == 1) {                                                     //Hace referencia al Enlace abajo
                datos.setSiguienteNodo(3);
            }

            datos.setTipoNodo("nodoVerticeDerechoArriba");                      //Nodo vertice derecho arriba     
            datos.setFila(0);
            datos.setColumna(tam - 1);
            return datos;
        
        } else if (actual == matrizDeNodos[tam - 1][0]) {
            datos.setSiguienteNodo(rnd.nextInt(2) + 1);                         //Hace referencia al enlace de arriba y el derecho
            datos.setTipoNodo("nodoVerticeIzquierdaAbajo");                    //Nodo vertice izquuierda abajo     
            datos.setFila(tam - 1);
            datos.setColumna(0);
            return datos;

         //Llegada
        } else if (actual == matrizDeNodos[tam - 1][tam - 1]){
            datos.setSiguienteNodo(rnd.nextInt(2));                                  //Hace referencia al enlace izquierdo y el de arriba   
            datos.setTipoNodo("nodoLlegada");                                        //Nodo llegada             
            datos.setFila(tam - 1);
            datos.setColumna(tam - 1);
            return datos;
        }

        //Establecemos los tipos y los nodos siguientes haciendo referencia a las paredes que van a ser derribadas.
        for (int i = 0; i < matrizDeNodos.length; i++) {
            for (int j = 0; j < matrizDeNodos.length; j++) {
                if (actual == matrizDeNodos[0][j]) {                            
                    int aleatorio = rnd.nextInt(3);                             //Generamos un numero aleatorio
                    if (aleatorio == 0) {                                       
                        datos.setSiguienteNodo(0);                              //Hace referencia al enlace izq
                    }
                    if (aleatorio == 1) {
                        datos.setSiguienteNodo(2);                              //Hace referencia al enlace der
                    }
                    if (aleatorio == 2) {
                        datos.setSiguienteNodo(3);                              //Hace referencia al enlace abajo
                    }
                    datos.setTipoNodo("nodoSuperior");                                //Nodos parte superior      
                    datos.setFila(0);
                    datos.setColumna(j);
                    return datos;
                } else if (actual == matrizDeNodos[i][0]) {
                    datos.setSiguienteNodo(rnd.nextInt(3) + 1);                 //Hace referencia a los enlaces arriba, derecha, abajo
                    datos.setTipoNodo("nodoIzquierdo");                                //Nodos parte izquierda del laberinto   
                    datos.setFila(i);
                    datos.setColumna(0);
                    return datos;
                } else if (actual == matrizDeNodos[i][tam - 1]) {
                    int num = rnd.nextInt(3);
                    if (num == 0) {
                        datos.setSiguienteNodo(1);                              //Hace referencia al enlace arriba
                    }
                    if (num == 1) {
                        datos.setSiguienteNodo(0);                              //Hace referencia al enlace izq
                    }
                    if (num == 2) {
                        datos.setSiguienteNodo(3);                              //Hace referencia al enlace abajo
                    }
                    datos.setTipoNodo("nodoDerecho");                                //Nodos parte derecha laberinto     
                    datos.setFila(i);
                    datos.setColumna(tam - 1);
                    return datos;
                } else if (actual == matrizDeNodos[tam - 1][j]) {
                    datos.setSiguienteNodo(rnd.nextInt(3));                     //Hace referencia a los enlaces izq, arriba y der
                    datos.setTipoNodo("nodoInferior");                                //Nodos para inferior laberinto         nodo nj
                    datos.setFila(tam - 1);
                    datos.setColumna(j);
                    return datos;
                } else if (actual == matrizDeNodos[i][j]) {
                    datos.setSiguienteNodo(rnd.nextInt(4));                     //Hace referencia a los cuatro enlaces
                    datos.setTipoNodo("nodoComun");                            //Nodo normal que no forma parte de los limites
                    datos.setFila(i);
                    datos.setColumna(j);
                    return datos;
                }
            }
        }
        return datos;
    }

    //Metodo que genera un laberinto cuadrado con una salida y una llegada pre-establecida
    public void generarLaberinto() {
        NodoArbol actual = arbol.getRaiz();
        actual.setVisitado(true);
        int cantVisitados = 1;
        InfoNodoActual datos;
        while (cantVisitados != tam * tam) {
            datos = datos(actual);

            //Vemos si los nodos adyacentes han sido visitados.
            switch (datos.getTipoNodo()) {
                case "nodoSalida":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoVerticeDerechoArriba":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoVerticeIzquierdaAbajo":
                    if (matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado()) {       //Vemos si los nodos adyacentes estan visitados
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoLlegada":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoSuperior":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoIzquierdo":
                    if (matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoInferior":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoDerecho":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
                case "nodoComun":
                    if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado() && matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado() && matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado()) {
                        actual = actual.getAnterior();
                        continue;
                    }
                    break;
            }

            
            //Setteamos los enlaces.
            //Establecemos el enlace izq
            if (datos.getSiguienteNodo() == 0) {
                if (matrizDeNodos[datos.getFila()][datos.getColumna() - 1].isVisitado()) {//Si este nodo ya fue visitado que continue
                    continue;
                } else {
                    actual.setEnlaceIzquierdo(matrizDeNodos[datos.getFila()][datos.getColumna() - 1]);      //
                    
                    NodoArbol anterior = actual;
                    actual = actual.getEnlaceIzquierdo();
                    actual.setAnterior(anterior);
                    actual.setVisitado(true);
                    cantVisitados++;
                }
                //Establecemos el enlace arriba
            } else if (datos.getSiguienteNodo() == 1) {
                if (matrizDeNodos[datos.getFila() - 1][datos.getColumna()].isVisitado()) {
                    continue;
                } else {
                    actual.setEnlaceArriba(matrizDeNodos[datos.getFila() - 1][datos.getColumna()]);
                    
                    NodoArbol anterior = actual;
                    actual = actual.getEnlaceArriba();
                    actual.setAnterior(anterior);
                    actual.setVisitado(true);
                    cantVisitados++;
                }
                //Establecemos el enlace derecho
            } else if (datos.getSiguienteNodo() == 2) {
                if (matrizDeNodos[datos.getFila()][datos.getColumna() + 1].isVisitado()) {
                    continue;
                } else {
                    actual.setEnlaceDerecho(matrizDeNodos[datos.getFila()][datos.getColumna() + 1]);
                    
                    NodoArbol anterior = actual;
                    actual = actual.getEnlaceDerecho();
                    actual.setAnterior(anterior);
                    actual.setVisitado(true);
                    cantVisitados++;
                }
                //Establecemos el enlace de abajo
            } else if (datos.getSiguienteNodo() == 3) {
                if (matrizDeNodos[datos.getFila() + 1][datos.getColumna()].isVisitado()) {
                    continue;
                } else {
                    
                    actual.setEnlaceAbajo(matrizDeNodos[datos.getFila() + 1][datos.getColumna()]);
                    
                    NodoArbol anterior = actual;
                    actual = actual.getEnlaceAbajo();
                    actual.setAnterior(anterior);
                    actual.setVisitado(true);
                    cantVisitados++;
                }
            }
        }
        arbol.setFin(matrizDeNodos[tam - 1][tam - 1]);                          //Establecemos nodo final del arbol
    }

    
    
    //Getters y setters
    public int getTam() {
        return tam;
    }

    public void setTam(int n) {
        this.tam = n;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public NodoArbol[][] getMatrizDeNodos() {
        return matrizDeNodos;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

}
