import java.util.*;

public class MaisExsSobreArrays03 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = 0;

    System.out.println("Insira o tamanho do vetor:\n");

    n = sc.nextInt();

    int[] arr = new int[n];

    int maior = arr[0];

    System.out.println("Insira os valores:\n");

    for (int i = 0; i < n; i++) {
      if (sc.hasNextInt()) {
        arr[i] = sc.nextInt();
      }
    }

    for (int i = 0; i < arr.length; i++) {
      if (maior < arr[i]){
        maior = arr[i];
      }
    }

    System.out.println("Maior numero da array: " + maior);


    sc.close();
  }
}