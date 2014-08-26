// CS1020 (AY2013/4 Semester 2)
// Take-home lab #4
// Name: Eugene
// Matric. No.: A0116631N
// Lab group: 7
import java.util.*;

/***
 * Compute the minimum amount of stacks required in order to successfully
 * unload the received cargo and load it to the ship.
 * 
 * Cargo Stacks must be in alphabetically ordered 
 * with the top being the lowest character ordinal value (e.g. A) of the stack
 * But the cargo may not arrived in ordered manner
 *  
 * @author Eugene
 *
 */
public class CargoOptimization {

    private ArrayList<Stack<Container>> stacks = new ArrayList<Stack<Container>>();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Start CargoOptimization App.
        CargoOptimization car_opt = new CargoOptimization();
        int min_stacks_required = car_opt.compute_min_stacks(s.nextLine());

        System.out.println(min_stacks_required);
    }

    /***
     * 
     * Compute the Minimum stacks required for the cargo to be successfully 
     * unloaded into the port. The stacks must be in alpha ordered. 
     * 
     * @param labels  the arrival cargo to be loaded to the ship
     * @return the minimum stack required for all unloaded cargo
     */
    public int compute_min_stacks(String labels) {

        char[] destinations_arr = labels.toUpperCase().toCharArray();

        for (char destination: destinations_arr){
            Container newArrival = new Container(destination);
            
            // Decide where best to place the container
            int best_stack_index = this.findBestStack(newArrival);
            
            // Prepare a new stack whenever needed. 
            // or simply just get the targeted stack
            Stack<Container> target_stack;
            try {
                target_stack = stacks.get(best_stack_index);
            } catch (IndexOutOfBoundsException e) {
                target_stack = new Stack<Container>();
                stacks.add(target_stack);
            }
            
            // Place the newArrival Container
            target_stack.push(newArrival);
        }
        
        return stacks.size();
    }

    /***
     * Returns the index of the best stack to put the container. 
     * the index value returned may not exists currently. 
     * Therefore, you have to catch for an IndexOutOfBoundException,
     * if you decide to do something about it.
     * 
     * @param newArrival the newly arrived labeled container. 
     * @return the index of the stacks where the container should be placed.
     */
    public int findBestStack(Container newArrival) {

        if (stacks.size() == 0) {
            return 0;
        }

        for (int i = 0; i < stacks.size(); i++) {
            Stack<Container> candidate = stacks.get(i);
            int container_fit_with = newArrival.calculateFitWith(candidate);
            
            // if the label of the container is smaller in alpha order.
            // then the current stack is the best.
            if (container_fit_with <= 0) {
                return i;
            }
        }
        
        // Since No Stacks can fit the newArrival container, 
        // we shall provide a new index outside of the existing lists.
        return stacks.size();
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

    /***
     * Return compatibility score between this container and the target stack.
     * they are most compatible when the container at the top of the stack has
     * the same destination as this container.
     * 
     * Negative value means,
     * this container is lower in alpha order then the candidate.
     * 
     * Positive value means,
     * this container is higher in alpha order then the candidate.
     * 
     * @param candidate the stacks that you want to compare with.
     * @return the label gap between this container and the top of the 
     *         candidate container.
     */
    public int calculateFitWith(Stack<Container> candidate) {
        Container top = candidate.peek();
         
        return this.getDestination() - top.getDestination();
    }
}
