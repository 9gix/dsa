import java.util.*;

class SchedulingDeliveriesVerifier {
  private static void checkName(String name) {
    if (name.length() > 15)
      System.out.println("ERROR, name " + name + " is longer than 15 characters");
    for (int i = 0; i < name.length(); i++)
      if (!Character.isUpperCase(name.charAt(i))) {
        System.out.println("ERROR, name " + name + " contains non-uppercase character");
        break;
      }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    TreeSet<String> women = new TreeSet<String>();
    String name;

    int numCMD = sc.nextInt();
    if (numCMD > 100000) {
      System.out.println("ERROR, too many commands...");
      return;
    }

    while (numCMD-- > 0) {
      int cmd = sc.nextInt();
      switch (cmd) {
        case 0:
          name = sc.next();
          checkName(name);
          if (women.contains(name)) {
            System.out.println("ERROR, duplicate woman name found..." + name);
            return;
          }
          women.add(name);
          int dilation = sc.nextInt();
          if (dilation < 30 || dilation > 100) {
            System.out.println("ERROR, dilation is not in [30..100]..., it is = " + dilation);
            return;
          }
          break;
        case 1:
          name = sc.next();
          checkName(name);
          if (!women.contains(name)) {
            System.out.println("ERROR, this woman " + name + " has not arrived in hospital yet...");
            return;
          }
          int increaseDilation = sc.nextInt();
          if (increaseDilation < 0 || increaseDilation > 70) {
            System.out.println("ERROR, increaseDilation is not in [0..70]..., it is = " + increaseDilation);
            return;
          }
          break;
        case 2:
          name = sc.next();
          checkName(name);
          if (!women.contains(name)) {
            System.out.println("ERROR, this woman " + name + " has not arrived in hospital yet...");
            return;
          }
          break;
        case 3:
          break;
      }
    }

    System.out.println("Test data is valid :)");
  }
}
