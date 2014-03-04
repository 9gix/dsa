package lab4.ex1;

// CS1020 (AY2013/4 Semester 2)
// Take-home lab #4
// Name: 
// Matric. No.: 
// Lab group: 
// Write the program description below.
// It is mandatory to write program description at the top of every program.
// Marks will be awarded for this in sit-in labs.
// Please remove this line and its preceding 3 lines.

import java.util.*;

public class CargoOptimization {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        // use an array list to represent the stacks in the terminal
        ArrayList<Stack<Container>> stacks = new ArrayList<Stack<Container>>();

        // complete the program

    }

    // returns the index of the best stack to put the container
    public static int findBestStack(Container newArrival,
            ArrayList<Stack<Container>> stacks) {

        // complete the method

        return 0;
    }
}

class Container {
    private char destination;

    public Container(char destination) {
        this.destination = destination;
    }

    public char getDestination() {
        return destination;
    }

    // method should return compatibility score between this container and the
    // target stack.
    // they are most compatible when the container at the top of the stack has
    // the same destination as this container
    public int calculateFitWith(Stack<Container> candidate) {

        // complete the method

        return 0;
    }
}
