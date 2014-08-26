import java.util.*;

class BabyNamesVerifier {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int maxL = 0;
    int N = 0, Q = 0;
    TreeSet<String> names = new TreeSet<String>();
    while (true) {
      int command = sc.nextInt();
      if (command == 0) // end of input
        break;
      else if (command == 1) { // Add Suggestion
        String name = sc.next();
        int gender = sc.nextInt();
        if (names.contains(name)) { // make it unique
          System.out.println("NOT UNIQUE: " + name + " " + gender);
          return;
        }
        if (name.length() > 30) {
          System.out.println("LONGER THAN 30 CHARS: " + name);
          return;
        }
        if (gender != 1 && gender != 2) {
          System.out.println("ERROR, GENDER MUST BE 1 OR 2");
          return;
        }
        names.add(name);
        N++;
      }
      else { // if (command == 2) // Query
        String START = sc.next();
        String END = sc.next();
        if (START.length() > 30 || END.length() > 30) {
          System.out.println("LONGER THAN 30 CHARS: " + START + ", " + END);
          return;
        }
        if (START.compareTo(END) >= 0) {
          System.out.println("ERROR, START MUST BE < END");
          return;
        }
        int gender = sc.nextInt();
        if (gender < 0 || gender > 2) {
          System.out.println("ERROR, GENDER MUST BE 0, 1, OR 2");
          return;
        }
        Q++;
      }
    }

    if (N > 20000) {
      System.out.println("ERROR, TOO MANY BABY NAMES");
      return;
    }

    if (Q > 20000) {
      System.out.println("ERROR, TOO MANY QUERIES");
      return;
    }

    System.out.println("Test data is valid");
  }
}
