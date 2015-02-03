import java.util.*;

class OutForAWalkVerifier {
  static int[][] AdjMat;

  private static Vector < Integer > visited;

  private static void DFSrec(int u) {
    visited.set(u, 1);
    for (int j = 0; j < AdjMat[u].length; j++)
      if (AdjMat[u][j] > 0 && visited.get(j) == 0)
        DFSrec(j);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int i, j, k, w, TC = sc.nextInt(), V, Q;

    while (TC-- > 0) {
      V = sc.nextInt();
      AdjMat = new int[V][V];
      for (i = 0; i < V; i++)
        for (j = 0; j < V; j++)
          AdjMat[i][j] = 0;

      for (i = 0; i < V; i++) {
        k = sc.nextInt();
        while (k-- > 0) {
          j = sc.nextInt();
          w = sc.nextInt();
          if (w < 0 || w > 1000) {
            System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, EFFORT RATING MUST BE IN [0..1000], it is = " + w);
            return;
          }
          AdjMat[i][j] = w;
        }
      }

      for (i = 0; i < V; i++)
        for (j = 0; j < V; j++)
          if (AdjMat[i][j] != AdjMat[j][i]) {
            System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, GRAPH MUST BE UNDIRECTED");
            return;
          }

      // Check if the graph is connected
      visited = new Vector < Integer > (V);
      visited.addAll(Collections.nCopies(V, 0));
      DFSrec(0);
      for (i = 0; i < V; i++)
        if (visited.get(i) == 0) {
          System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, GRAPH MUST BE CONNECTED");
          return;
        }

      Q = sc.nextInt();
      while (Q-- > 0) {
        i = sc.nextInt();
        if (i < 0 || i > 9) {
          System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, SOURCE VERTEX MUST BE IN [0..9], it is = " + i);
          return;
        }
        j = sc.nextInt();
        if (j < 0 || j > V - 1) {
          System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, DESTINATION VERTEX MUST BE IN [0..V-1], it is = " + j);
          return;
        }
      }
    }

    System.out.println("Test data is valid :)");

    // Note, please scrutinize Sample.txt and Sample-ans.txt carefully
    // especially the positioning of blank lines to separate two test cases

    // This verifier program does not do this check yet.
  }
}
