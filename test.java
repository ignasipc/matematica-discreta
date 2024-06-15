import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * test
 */
public class test {

   public static void main(String[] args) {
    exercici2(2, 2, 4);
    
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

  
    
}
  