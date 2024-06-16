import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb "// TODO". L'enunciat de
 * cada un d'ells és al comentari de la seva signatura i exemples del seu funcionament als mètodes
 * `Tema1.tests`, `Tema2.tests`, etc.
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà un 0.
 *
 * - Si heu fet cap modificació que no sigui afegir un mètode, afegir proves als mètodes "tests()" o
 *   implementar els mètodes annotats amb "// TODO", la nota del grup serà un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implemnetats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Algunes
 *   consideracions importants:
 *    + Entregau amb la mateixa codificació (UTF-8) i finals de línia (LF, no CR+LF)
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:
 * - Nom 2:
 * - Nom 3:Asier zubillaga llabres
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * La majoria dels mètodes reben de paràmetre l'univers (representat com un array) i els predicats
   * adients (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un
   * element de l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si
   * `P(x)` és cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis (excepte el primer) us demanam que donat l'univers i els
   * predicats retorneu `true` o `false` segons si la proposició donada és certa (suposau que
   * l'univers és suficientment petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * Donat n > 1, en quants de casos (segons els valors de veritat de les proposicions p1,...,pn)
     * la proposició (...((p1 -> p2) -> p3) -> ...) -> pn és certa?
     *
     * Vegeu el mètode Tema1.tests() per exemples.
     */
    static int exercici1(int n) {
      boolean tablaValores [][] = new boolean [dosElevadoA(n)][n];
        
        int intercambios = dosElevadoA(n);
        
        for (boolean[] fila : tablaValores) {
            for (int j = 0; j < fila.length; j++) {
                fila[j] = true;
            }
        }
        
        for (int i = 0; i < tablaValores[0].length; i++) {
            intercambios /= 2;
            int contador = 0;
            for (boolean[] tablaValore : tablaValores) {
                tablaValore[i] = contador >= intercambios;
                contador++;
                if (contador == 2 * intercambios) {
                    contador = 0;
                }
            }
        }
        
        int numeroTrues = 0;
        for (boolean[] fila : tablaValores) {
            boolean resultado = fila[0];
            for (int j = 0; j < fila.length - 1; j++) {
                resultado = !(resultado && !fila[j+1]);
            }
            if (resultado) {
                numeroTrues++;
            }
        }
        return numeroTrues;
    }

    static int dosElevadoA(int n){
        return 1 << n;
    }

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      boolean resultado [] = new boolean [universe.length];
        for (int i = 0; i < universe.length; i++) { //∀x
            if (p.test(universe[i])) {              //:P(x) == true
                int existe = 0;
                
                for (int j = 0; j < universe.length; j++) {
                    if (q.test(universe[i], universe[j])) {  //Si ∃y : Q(x,y)
                        existe++;
                    }
                }
                
                if (existe == 1) {                  //∃!y : Q(x,y) == true
                    resultado[i] = true;
                }else{                              //∃!y : Q(x,y) == false
                    resultado[i] = false;
                }
            }else{                                  //:P(x) == false, sempre es true
                resultado[i] = true;
            }
        }
        
        int contadorTrue = 0;
        for (int i = 0; i < resultado.length; i++) {
            if (resultado[i]) {
                contadorTrue++;
            }
        }
        
        if (contadorTrue == resultado.length) {
            return true;
        }else{
            return false;
        }
    }

    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
        boolean resultado [] = new boolean [universe.length];
        
        //Buscamos si existen x tales que ∀y : Q(x, y) -> P(x)
        for (int i = 0; i < universe.length; i++) {
            int numeroTrues = 0;
            for (int j = 0; j < universe.length; j++) {
                if (q.test(universe[i], universe[j])) {     //Q(x, y) = true
                    if (p.test(universe[i])) {              //P(x) = true
                        numeroTrues++;
                    }                                       //P(x) = false
                }else{                                      //Q(x, y) = false, entonces siempre es cierto
                    numeroTrues++;
                }
            }
            if (numeroTrues==universe.length) {             //Si es cierto ∀y dada una x, 
                resultado[i] = true;
            }else{
                resultado[i] = false;
            }
        }
        //Comprobamos cuantas veces es cierto
        int contadorTrue = 0;
        for (int i = 0; i < resultado.length; i++) {
            if (resultado[i]) {
                contadorTrue++;
            }
        }
        //Es cierto si existe 1 o mas veces
        if (contadorTrue > 0) {
            return true;
        }else{
            return false;
        }
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
        boolean resultadosX [] = new boolean [universe.length];
        boolean resultadosY [] = new boolean [universe.length];
        boolean resultadosZ [] = new boolean [universe.length];
        
        for (int x = 0; x < universe.length; x++) {
            for (int y = 0; y < universe.length; y++) {
                for (int z = 0; z < universe.length; z++) {
                    if (!p.test(universe[x], universe[z])) {                    //Si P(x,z) = False
                        if (!q.test(universe[y], universe[z])) {                //Si Q(y,z) = False
                            resultadosZ[z] = true;
                        }else{                                                  //Si Q(y,z) = True
                            resultadosZ[z] = false;
                        }
                    }else{                                                      //Si Q(y,z) = True
                        if (!q.test(universe[y], universe[z])) {                //Si Q(y,z) = False
                            resultadosZ[z] = false;
                        }else{                                                  //Si Q(y,z) = True
                            resultadosZ[z] = true;
                        }
                    }
                }
                int contadorZ = 0;
                for (int i = 0; i < universe.length; i++) {
                    if (resultadosZ[i]) {
                        contadorZ++;
                    }
                }
                if (contadorZ==universe.length) {                               //Comprobamos que se cumple ∀z
                    resultadosY[y] = true;
                }else{
                    resultadosY[y] = false;
                }
            }
            int contadorY = 0;
            for (int i = 0; i < universe.length; i++) {
                if (resultadosY[i]) {
                    contadorY++;
                }
            }
            if (contadorY==1) {                                                 //Comprobamos que se cumple ∃!y
                resultadosX[x] = true;
            }else{
                resultadosX[x] = false;
            }
        }
        int contadorX = 0;
        for (int i = 0; i < universe.length; i++) {
            if (resultadosX[i]) {
                contadorX++;
            }
        }
        if (contadorX > 0) {                                                    //Comprobamos que se cumple ∃x 1 o mas x
            return true;
        }else{
            return false;
        }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1

      // p1 -> p2 és cert exactament a 3 files
      // p1 p2
      // 0  0  <-
      // 0  1  <-
      // 1  0
      // 1  1  <-
      assertThat(exercici1(2) == 3);

      // (p1 -> p2) -> p3 és cert exactament a 5 files
      // p1 p2 p3
      // 0  0  0
      // 0  0  1  <-
      // 0  1  0
      // 0  1  1  <-
      // 1  0  0  <-
      // 1  0  1  <-
      // 1  1  0
      // 1  1  1  <-
      assertThat(exercici1(3) == 5);

      // Exercici 2
      // ∀x : P(x) -> ∃!y : Q(x,y)
      assertThat(
          exercici2(
            new int[] { 1, 2, 3 },
            x -> x % 2 == 0,
            (x, y) -> x+y >= 5
          )
      );

      assertThat(
          !exercici2(
            new int[] { 1, 2, 3 },
            x -> x < 3,
            (x, y) -> x-y > 0
          )
      );

      // Exercici 3
      // És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 3 != 0,
            (x, y) -> y % x == 0
          )
      );

      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 4 != 0,
            (x, y) -> (x*y) % 4 != 0
          )
      );

      // Exercici 4
      // És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
      assertThat(
          exercici4(
            new int[] { 0, 1, 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );

      assertThat(
          !exercici4(
            new int[] { 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a, el
   * codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per aplicar
   * f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu f.apply(x).
   */
  static class Tema2 {
    /*
     * Calculau el nombre d'elements del conjunt (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
        //Calculamos el conjunto de (A U B)
        
        //r1 tamaño A + B
        int r1 [] = new int [(a.length+b.length)];
        for (int i = 0; i < a.length; i++) {
            r1[i] = a[i];
        }
        //r1 = A
        
        //Insertamos B en r1, comprobando si ya está incluido o no
        int posColocar = a.length;
        for (int i = 0; i < b.length; i++) {
            boolean yaEstaIncluido = false;
            for (int j = 0; j < a.length; j++) {
                if(b[i]==a[j]){
                    yaEstaIncluido = true;
                }
            }
            if(!yaEstaIncluido){
                r1[posColocar] = b[i];
                posColocar ++;
            }
        }
        
        //r1 = A U B U {0, 0, 0...}, los quitamos creando R1
        int R1[] = new int [posColocar];
        for (int i = 0; i < R1.length; i++) {
            R1[i] = r1[i];
        }
        //R1 = A U B
        
        //Calculamos el conjunto de (A \ C)
        
        //r2 tamaño A
        int r2[] = new int [a.length];
        int posAlta = a.length;
        
        int k = 0;
        //Buscamos todos los componentes de C en A y los eliminamos de r2
        for (int i = 0; i < a.length; i++) {
            boolean estaIncluido = false;
            for (int j = 0; j < c.length; j++) {
                if (a[i]==c[j]) {
                    estaIncluido = true;
                    posAlta--;
                }
            }
            if (!estaIncluido) {
                r2[k] = a[i];
                k++;
            }
        }
        
        //r2 = A \ C U {0, 0, 0...}, los quitamos creando R2
        int R2[] = new int [posAlta];
        for (int i = 0; i < R2.length; i++) {
            R2[i] = r2[i];
        }
        //R2 = A \ C
        
        //Entonces el numero de componentes de hacer R1 x R2 = nºComponentes R1 x nºComponentes R2
        return (R1.length*R2.length);
    }

    /*
     * La clausura d'equivalència d'una relació és el resultat de fer-hi la clausura reflexiva, simètrica i
     * transitiva simultàniament, i, per tant, sempre és una relació d'equivalència.
     *
     * Trobau el cardinal d'aquesta clausura.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici2(int[] a, int[][] rel) {
        //Obtenemos la reflexiva
        int [][] reflexiva = generateRel(a,(x, y) -> x.intValue()==y);
        
        //Obtenemos la simetrica
        int [][] simetrica = new int[rel.length][2];
        
        for (int i = 0; i < rel.length; i++) {
            simetrica[i][1] = rel[i][0];
            simetrica[i][0] = rel[i][1];
        }
        
        //Antes de obtener la transitiva, juntamos todas las anteriores con rel
        int r1 [][] = juntarRelaciones(rel,simetrica);
        int r2 [][] = juntarRelaciones(r1,reflexiva);
        
        //Una vez obtenida, sacamos la transitiva
        boolean valoresDiferentes = true;
        int valorAnterior, valorActual;
        int [][] transitivaConDuplicados = r2;
        while(valoresDiferentes){
            valorAnterior = transitivaConDuplicados.length;
            transitivaConDuplicados = obtenerTransitiva(transitivaConDuplicados);
            valorActual = transitivaConDuplicados.length;
            if(valorAnterior==valorActual){
                valoresDiferentes = false;
            }
        }
        
        int clausuraEquiv[][] = eliminarDuplicados(juntarRelaciones(r2,transitivaConDuplicados));
        
        return clausuraEquiv.length;
    }

    static int[][] juntarRelaciones(int [][] a, int [][] b){
        int resultado [][] = new int [a.length+b.length][2];
        
        for (int i = 0; i < a.length; i++) {
            resultado[i][0] = a[i][0];
            resultado[i][1] = a[i][1];
        }
        //Insertamos simetrica en r1
        for (int i = a.length; i < a.length+b.length; i++) {
            resultado[i][0] = b[i-a.length][0];
            resultado[i][1] = b[i-a.length][1];
        }
        
        return resultado;
    }
    
    public static int[][] eliminarDuplicados(int[][] array) {
        // Obtener el tamaño del array original
        int n = array.length;

        // Crear un array temporal para almacenar los resultados sin duplicados
        int[][] tempArray = new int[n][2];
        // Contador para la longitud del nuevo array sin duplicados
        int nuevoLength = 0;

        // Iterar sobre cada fila del array original
        for (int i = 0; i < n; i++) {
            boolean estaDuplicado = false;
            // Comprobar si la fila actual ya ha aparecido antes
            for (int j = 0; j < nuevoLength; j++) {
                if (array[i][0] == tempArray[j][0] && array[i][1] == tempArray[j][1]) {
                    estaDuplicado = true;
                    break;
                }
            }
            // Si no es duplicada, agregarla al array temporal
            if (!estaDuplicado) {
                tempArray[nuevoLength][0] = array[i][0];
                tempArray[nuevoLength][1] = array[i][1];
                nuevoLength++;
            }
        }

        // Crear un array final con la longitud adecuada para almacenar los resultados sin duplicados
        int[][] resultado = new int[nuevoLength][2];
        for (int i = 0; i < nuevoLength; i++) {
            resultado[i][0] = tempArray[i][0];
            resultado[i][1] = tempArray[i][1];
        }

        return resultado;
    }
    
    public static int [][] obtenerTransitiva(int [][] r2){
        int [][] R1 = new int [r2.length*r2.length][2];
        int numeroResultados = 0;
        
        for (int i = 0; i < r2.length; i++) {
            int primerNumero = r2[i][0];
            int segundoNumero = r2[i][1];
            for (int j = 0; j < r2.length; j++) {
                if (segundoNumero==r2[j][0]) {
                    R1 [numeroResultados][0] = primerNumero;
                    R1 [numeroResultados][1] = r2[j][1];
                    numeroResultados++;
                }
            }
        }
        
        int R2[][] = new int [numeroResultados][2];
        for (int i = 0; i < R2.length; i++) {
            for (int j = 0; j < 2; j++) {
                R2[i][j] = R1[i][j];
            }
        }
        
        return eliminarDuplicados(R2);
    }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) {
        //Comprobam si es reflexiva
        int [][] reflexiva = generateRel(a,(x, y) -> x.intValue()==y);
        boolean esReflexiva = comprobarRelacion(reflexiva,rel);
        
        //Comprobar si es antisimetrica
        boolean esAntisimetrica = comprobarAntisimetria(a,rel);
        
        //Comprobar si es transitiva
        boolean valoresDiferentes = true;
        int valorAnterior, valorActual;
        int [][] transitivaConDuplicados = rel;
        while(valoresDiferentes){
            valorAnterior = transitivaConDuplicados.length;
            transitivaConDuplicados = obtenerTransitiva(transitivaConDuplicados);
            valorActual = transitivaConDuplicados.length;
            if(valorAnterior==valorActual){
                valoresDiferentes = false;
            }
        }
        
        int transitiva[][] = eliminarDuplicados(juntarRelaciones(rel,transitivaConDuplicados));
        
        boolean esTransitiva = comprobarRelacion(transitiva,rel);
        
        boolean esOrdenTotal = comprobarOrdenTotal(a, rel);
        
        if (esReflexiva && esAntisimetrica && esTransitiva && esOrdenTotal) {
            return a.length-1;
        }
        return -2;
    }

    public static boolean sonTodoTrue(boolean[] array) {
        for (int i = 0; i < array.length; i++) {
            if (!array[i]) {
                return false;  // Encontramos un false, no todos son true
            }
        }
        return true;  // No encontramos ningún false, todos son true
    }
    
    public static boolean comprobarRelacion(int [][] tipoRelacion, int [][] rel){
        boolean [] partesRelacion = new boolean [tipoRelacion.length];
        for (int i = 0; i < tipoRelacion.length; i++) {
            int numeroComprobar = tipoRelacion[i][0];
            for (int[] rel1 : rel) {
                if ((numeroComprobar == rel1[0]) && (numeroComprobar == rel1[1])) {
                    partesRelacion[i] = true;
                }
            }
        }
        
        return sonTodoTrue(partesRelacion);
    }
    
    public static boolean comprobarAntisimetria(int[] A, int[][] rel) {
        for (int[] rel1 : rel) {
            int a = rel1[0];
            int b = rel1[1];
            if (a != b) {
                for (int[] rel2 : rel) {
                    if (rel2[0] == b && rel2[1] == a) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static boolean comprobarOrdenTotal(int[] a, int[][] rel) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (i != j) {
                    boolean encontrado = false;
                    for (int[] rel1 : rel) {
                        if ((rel1[0] == a[i] && rel1[1] == a[j]) || (rel1[0] == a[j] && rel1[1] == a[i])) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
        int [][] composicion=new int[a.length][a.length];

        if (rel1.equals(rel2)) {
          for(int i=0;i<rel1.length;i++){
            for(int j=1;j<rel1[i].length;j++){
              if (rel1[i][j]==rel2[i][j-1]) {
                int p1=rel1[i][j];
                int p2=rel2[i][j-1];
                composicion[i][j-1]=p1;
                composicion[i][j]=p2;
              }
            }
          }
        }else{
          return null;
        }
    
        return composicion;
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
        Function<Integer, Integer> inverseF = getInverse(f);
        int[][] reverse=new int[dom.length][dom.length];
        for(int i=0;i<codom.length;i++){
        int result=inverseF.apply(codom[i]);
  
        boolean esta=false;
        for (int j=0;j<dom.length;j++){
          if (result==dom[j]) {
            esta=true;
            reverse[i][j]=result;
          }
        }
  
        if (!esta) {
          return new int[][] {};
        }
      }
      return reverse; // TODO
      }
  
      public static Function<Integer, Integer> getInverse(Function<Integer, Integer> f) {
        return x -> {
          int y = 0;
          while (f.apply(y) != x) {
              y++;
          }
          return y;
      };
      }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // |(a u b) × (a \ c)|

      assertThat(
          exercici1(
            new int[] { 0, 1, 2 },
            new int[] { 1, 2, 3 },
            new int[] { 0, 3 }
          )
          == 8
      );

      assertThat(
          exercici1(
            new int[] { 0, 1 },
            new int[] { 0 },
            new int[] { 0 }
          )
          == 2
      );

      // Exercici 2
      // nombre d'elements de la clausura d'equivalència

      final int[] int08 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(exercici2(int08, generateRel(int08, (x, y) -> y == x + 1)) == 81);

      final int[] int123 = { 1, 2, 3 };

      assertThat(exercici2(int123, new int[][] { {1, 3} }) == 5);

      // Exercici 3
      // Si rel és un ordre total, retornar les arestes del diagrama de Hasse

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(exercici3(int05, generateRel(int05, (x, y) -> x >= y)) == 5);
      assertThat(exercici3(int08, generateRel(int05, (x, y) -> x <= y)) == -2);

      // Exercici 4
      // Composició de grafs de funcions (null si no ho son)

      assertThat(
          exercici4(
            int05,
            generateRel(int05, (x, y) -> x*x == y),
            generateRel(int05, (x, y) -> x == y)
          )
          == null
      );


      var ex4test2 = exercici4(
          int05,
          generateRel(int05, (x, y) -> x + y == 5),
          generateRel(int05, (x, y) -> y == (x + 1) % 6)
        );

      assertThat(
          Arrays.deepEquals(
            lexSorted(ex4test2),
            generateRel(int05, (x, y) -> y == (5 - x + 1) % 6)
          )
      );

      // Exercici 5
      // trobar l'inversa (null si no existeix)

      assertThat(exercici5(int05, int08, x -> x + 3) == null);

      assertThat(
          Arrays.deepEquals(
            lexSorted(exercici5(int08, int08, x -> 8 - x)),
            generateRel(int08, (x, y) -> y == 8 - x)
          )
      );
    }

    /**
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
     */
    static int[][] lexSorted(int[][] arr) {
      if (arr == null)
        return null;

      var arr2 = Arrays.copyOf(arr, arr.length);
      Arrays.sort(arr2, Arrays::compare);
      return arr2;
    }

    /**
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
     */
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      var rel = new ArrayList<int[]>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }

    /// Especialització de generateRel per a = b
    static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
      return generateRel(as, as, pred);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
   *
   * **NOTA: Els exercicis d'aquest tema conten doble**
   */
  static class Tema3 {
    /*
     * Determinau si el graf és connex. Podeu suposar que `g` no és dirigit.
     */
    static boolean exercici1(int[][] g) {
        int numNodos = g.length;
        boolean[] nodosVisitados = new boolean[numNodos];
        //Empezar la recursividad desde el nodo 0
        recorrerGrafo(0, g, nodosVisitados);
        //comprobar si se han visitado todos los nodos
        for (boolean visitado : nodosVisitados) {
            if (!visitado) {
                return false;
            }
        }
        return true;
    }
    
    //Funcion recursiva para recorrer el grafo
    private static void recorrerGrafo(int nodo, int[][] g, boolean[] visitados) {
        //Marcamos el nodo actual como visitado
        visitados[nodo] = true;
        //Recorre todos los vecinos del nodo visitado
        for (int vecino : g[nodo]) {
            if (!visitados[vecino]) {
                recorrerGrafo(vecino, g, visitados);
            }
        }
    }

    /*
     * Donat un tauler d'escacs d'amplada `w` i alçada `h`, determinau quin és el mínim nombre de
     * moviments necessaris per moure un cavall de la casella `i` a la casella `j`.
     *
     * Les caselles estan numerades de `0` a `w*h-1` per files. Per exemple, si w=5 i h=2, el tauler
     * és:
     *   0 1 2 3 4
     *   5 6 7 8 9
     *
     * Retornau el nombre mínim de moviments, o -1 si no és possible arribar-hi.
     */
    static int exercici2(int w, int h, int i, int j) {
      return -1; // TO DO
    }

    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      return false; // TO DO
    }

    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {
      return -1; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // G connex?

      final int[][] B2 = { {}, {} };

      final int[][] C3 = { {1, 2}, {0, 2}, {0, 1} };

      final int[][] C3D = { {1}, {2}, {0} };

      assertThat(exercici1(C3));
      assertThat(!exercici1(B2));

      // Exercici 2
      // Moviments de cavall

      // Tauler 4x3. Moviments de 0 a 11: 3.
      // 0  1   2   3
      // 4  5   6   7
      // 8  9  10  11
      assertThat(exercici2(4, 3, 0, 11) == 3);

      // Tauler 3x2. Moviments de 0 a 2: (impossible).
      // 0 1 2
      // 3 4 5
      assertThat(exercici2(3, 2, 0, 2) == -1);

      // Exercici 3
      // u apareix abans que v al recorregut en preordre (o u=v)

      final int[][] T1 = {
        {1, 2, 3, 4},
        {5},
        {6, 7, 8},
        {},
        {9},
        {},
        {},
        {},
        {},
        {10, 11},
        {},
        {}
      };

      assertThat(exercici3(T1, 0, 5, 3));
      assertThat(!exercici3(T1, 0, 6, 2));

      // Exercici 4
      // Altura de l'arbre donat el recorregut en preordre

      final int[] P1 = { 0, 1, 5, 2, 6, 7, 8, 3, 4, 9, 10, 11 };
      final int[] D1 = { 4, 1, 3, 0, 1, 0, 0, 0, 0, 2,  0,  0 };

      final int[] P2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      final int[] D2 = { 2, 0, 2, 0, 2, 0, 2, 0, 0 };

      assertThat(exercici4(P1, D1) == 3);
      assertThat(exercici4(P2, D2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
   */
  static class Tema4 {
    /*
     * Calculau el mínim comú múltiple de `a` i `b`.
     */
    static int exercici1(int a, int b) {
        //variable a devolver
        int mcm=1;
        //incremento
        int i=2;

        while (i<=a || i<=b) {
            if (a%i==0 || b%i==0) {
                mcm=mcm*i;
                if (a%i==0) {
                    a=a/i;
                }
                if (b%i==0) {
                    b=b/i;
                }
            }else{
                i=i+1;
            }
        }
      return mcm; // TO DO
    }

    /*
     * Trobau totes les solucions de l'equació
     *
     *   a·x ≡ b (mod n)
     *
     * donant els seus representants entre 0 i n-1.
     *
     * Podeu suposar que `n > 1`. Recordau que no no podeu utilitzar la força bruta.
     */
    static int[] exercici2(int a, int b, int n) {
        ArrayList<Integer> solucion=new ArrayList<Integer>();

        int mcd=MCD(a, n);
  
        if (b%mcd==0) {
          for (int i=0;i<n;i++){
            if ((a*i)%n==b) {
              solucion.add(i);
            }
          }
          int [] sol=new int[solucion.size()];
  
          for(int i=0;i<sol.length;i++){
            sol[i]=solucion.get(i);
          }
          return sol;
        }

        return new int[]{}; // TO DO
    }

    /*
     * Donats `a != 0`, `b != 0`, `c`, `d`, `m > 1`, `n > 1`, determinau si el sistema
     *
     *   a·x ≡ c (mod m)
     *   b·x ≡ d (mod n)
     *
     * té solució.
     */
    static boolean exercici3(int a, int b, int c, int d, int m, int n) {
      boolean sol=false;

      int num1=MCD(a,m);

      int num2=MCD(b, n);

      if ((c % num1)==0 && (d % num2)==0) {
        if (MCD(m,n)==1) {
          sol=true;
        }else{
          sol=false;
        }
        
      }
      return sol; 
    }

    static int MCD(int a, int b){
      
      while(a!=b) {
        if (a>b) {
          a=a-b;
        }else{
          b=b-a;
        }
      }

      return a;
    }

  

    /*
     * Donats `n` un enter, `k > 0` enter, i `p` un nombre primer, retornau el residu de dividir n^k
     * entre p.
     *
     * Alerta perquè és possible que n^k sigui massa gran com per calcular-lo directament.
     * De fet, assegurau-vos de no utilitzar cap valor superior a max{n, p²}.
     *
     * Anau alerta també abusant de la força bruta, la vostra implementació hauria d'executar-se en
     * qüestió de segons independentment de l'entrada.
     */
    static int exercici4(int n, int k, int p) {
        int resultado = 1;
      //cualquier numero elevado a 0 es igual a 1
      //y el resto de uno entre cualquier numero mayor a el es 1
      if (k == 0) {
        return 1;
    }

    if (n < 0) {
        n = n % p + p;
    } else {
        n = n % p;
    }
 
    while (k > 0) {
        if (k % 2 == 1) {
          resultado = (resultado * n) % p;
        }
        n = (n * n) % p;
        k = k/2;
    }
    return resultado;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // mcm(a, b)

      assertThat(exercici1(35, 77) == 5*7*11);
      assertThat(exercici1(-8, 12) == 24);

      // Exercici 2
      // Solucions de a·x ≡ b (mod n)

      assertThat(Arrays.equals(exercici2(2, 2, 4), new int[] { 1, 3 }));
      assertThat(Arrays.equals(exercici2(3, 2, 4), new int[] { 2 }));

      // Exercici 3
      // El sistema a·x ≡ c (mod m), b·x ≡ d (mod n) té solució?

      assertThat(exercici3(3, 2, 2, 2, 5, 4));
      assertThat(!exercici3(3, 2, 2, 2, 10, 4));

      // Exercici 4
      // n^k mod p

      assertThat(exercici4(2018, 2018, 5) == 4);
      assertThat(exercici4(-2147483646, 2147483645, 46337) == 7435);
    }
  }

  /**
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
