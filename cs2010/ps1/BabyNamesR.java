import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here (reading someone's post in Facebook group and using the idea is counted as collaborating):

class BabyNamesR {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public BabyNamesR() {
    // Write necessary codes during construction;
    //
    // write your answer here



  }

  void AddSuggestion(String babyName) {
    // You have to insert the information: babyName
    // into your chosen data structure
    //
    // write your answer here



  }

  int Query(String SUBSTR) {
    int ans = 0;

    // You have to answer how many baby names contain substring SUBSTR
    //
    // write your answer here



    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int N = Integer.parseInt(br.readLine());
    while (N-- > 0)
      AddSuggestion(br.readLine());

    int Q = Integer.parseInt(br.readLine());
    while (Q-- > 0)
      pr.println(Query(br.readLine())); // SUBSTR
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    BabyNamesR ps1R = new BabyNamesR();
    ps1R.run();
  }
}
