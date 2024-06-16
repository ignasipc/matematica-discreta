public class test {

   public static void main(String[] args) {
    
    System.out.println(exercici4(2018, 2018, 5) == 4);
    System.out.println(exercici4(-2147483646, 2147483645, 46337) == 7435);
    
  }

   
    static int exercici4(int n, int k, int p) {
      int resultado = 1;

      // Cualquier número elevado a 0 es igual a 1
      if (k == 0) {
          return 1;
      }

      // Corregir n para que esté dentro del rango [0, p)
      if (n < 0) {
          n = n % p + p;
      } else {
          n = n % p;
      }

      while (k > 0) {
          // Si el exponente es impar, multiplica el resultado por la base
          if (k % 2 == 1) {
              resultado = (resultado * n) % p;
          }

          // Eleva la base al cuadrado
          n = (n * n) % p;

          // Divide el exponente entre 2
          k = k / 2;
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
  