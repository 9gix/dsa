import java.util.*;

class SupermarketVerifier {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int i, j, N, K, TC = sc.nextInt(), v;
    int[][] AdjMat = new int[1010][1010];

    while (TC-- > 0) {
      N = sc.nextInt(); K = sc.nextInt();

      for (i = 0; i < K; i++) {
        v = sc.nextInt();
        if (v < 1 || v > N) {
          System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, SHOPLIST MUST BE WITHIN [1..N]");
          return;
        }
      }

      // read (N+1) * (N+1) matrix
      for (i = 0; i <= N; i++)
        for (j = 0; j <= N; j++) {
          AdjMat[i][j] = sc.nextInt();
          if (AdjMat[i][j] < 0) {
            System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, MATRIX CONTENT MUST BE NON-NEGATIVE");
            return;
          }
        }

      for (i = 0; i <= N; i++)
        for (j = 0; j <= N; j++)
          if (AdjMat[i][j] != AdjMat[j][i]) {
            System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, MATRIX MUST BE SYMMETRIC");
            return;
          }

      for (i = 0; i <= N; i++)
        if (AdjMat[i][i] != 0) {
          System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, DIAGONAL MUST BE 0");
          return;
        }
    }

    System.out.println("Test data is valid :)");
  }
}
