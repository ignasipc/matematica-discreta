public class test {

   public static void main(String[] args) {
    
    System.out.println(exercici4(-2147483646, 2147483645, 46337));
    
  }

   
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
  