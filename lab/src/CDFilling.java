// CS1020 (AY2013/4 Semester 2)
// Take-home lab #5
// Name: Eugene
// Matric. No.: A0116631N
// Lab group: 7
/**
 * Application for Optimizing the maximum space in CD Filling of Songs
 */

import java.util.*;


/***
 * Abstraction of Songs Information or MetaData
 * Currently only Size that matters
 */
class Song {
    private int size;
    public Song(int size){
        this.size = size;
    }
    
    public int getSize(){
        return this.size;
    }
    
    public String toString() {
        return String.valueOf(this.getSize());
    }
}

/***
 * Executable Class for CD Filling application of Song. 
 * When executing, the first args is the maximum capacity of the cd, 
 * the second args is the number of songs will be provided,
 * the thirds args onwards are the size of each songs.
 */
public class CDFilling {

    public static void main(String[] args) {
        CDFilling app = new CDFilling();
        app.run();
    }

    public void run(){

        try (Scanner sc = new Scanner(System.in)) { // close scanner when done
            while (sc.hasNextLine()) {
                String line= sc.nextLine();
                
                // Tokenize with the following format 
                // <cd capacity> <n_songs> <n1> <n2> ... <n-th_songs> 
                String[] token = line.split("\\s");

                this.max_capacity = Integer.parseInt(token[0]);
                int n_songs = Integer.parseInt(token[1]);

                // Organizing data structure from the raw input
                LinkedList<Song> all_disc = new LinkedList<Song>();
                for (int i=0; i < n_songs; i++) {
                    int disc_size = Integer.parseInt(token[i+2]);
                    all_disc.offer(new Song(disc_size));
                }

                // Starting with empty songs to be filled in the cd
                LinkedList<Song> empty_songs = new LinkedList<Song>();
                
                // Start calculating the best songs for the cd
                cd_songs = this.maximizeDisc(empty_songs, all_disc);
                
                // Print the Songs sizes followed by the total sum at the end
                System.out.print(this + "\n");
            
            }
        }
    }
    
    // CD Maximum Capacity
    private int max_capacity = 0;
    
    // Selected Songs to be filled into the CD
    private LinkedList<Song> cd_songs = new LinkedList<Song>();
    
    public String toString() {
        String result = "";
        for (Song song: cd_songs){
            result += song.toString() + " ";
        }
        result += this.sum_songs_size(cd_songs);
        
        return result;
    }
    
    /**
     * Sum the size of Songs 
     * 
     * @param songs any collections (linkedlist, arraylist, etc.)
     * @return
     */
    public int sum_songs_size(Collection<Song> songs){
        int sum = 0;
        for (Song song: songs){
            sum += song.getSize();
        }
        return sum;
    }
    
    /***
     * This functions calculate and return the list of songs 
     * that is best fit to be filled into the CD.
     * 
     * It is implemented with a recursive call, 
     * to permutate the different possibility 
     * 
     * @param included_songs started with empty songs, it recursively fill the songs
     * @param remaining_songs the remaining of the songs in the permutation
     * @return  list of songs
     */
    public LinkedList<Song> maximizeDisc(
            LinkedList<Song> included_songs, 
            LinkedList<Song> remaining_songs) {
        
        if (remaining_songs.isEmpty()){ // Base case: No remaining songs
            if (this.sum_songs_size(included_songs) > this.max_capacity){
                // clear all the included songs 
                // whenever it exceed the maximum CD capacity
                included_songs.clear();
            }
            
            // Return the songs to be included
            return included_songs;                
        } else {
            // remove the first songs in the remaining songs queue.
            Song song = remaining_songs.poll();
            
            // Permutation 1: Do not pick the remaining songs
            LinkedList<Song> excluded_next_songs = maximizeDisc(
                    new LinkedList<Song>(included_songs), 
                    new LinkedList<Song>(remaining_songs));
            
            
            // Permutation 2: Pick the first of the Remaining Songs
            included_songs.offer(song);            
            LinkedList<Song> included_next_songs = maximizeDisc(
                    new LinkedList<Song>(included_songs), 
                    new LinkedList<Song>(remaining_songs));
            
            // Summing the Size of the songs from permuatation 1 and 2
            int included_sum = this.sum_songs_size(included_next_songs);
            int excluded_sum = this.sum_songs_size(excluded_next_songs);
            
            // Return either permutation 1 songs or permutation 2 songs 
            // depending on the maximum
            if ( included_sum > excluded_sum){
                return included_next_songs;
            } else if (included_sum < excluded_sum){
                return excluded_next_songs;
            } else {
                // If the sum are equal, potentially both are empty. 
                // So just return any of the permutation
                return included_next_songs;
            }
        }
    }
}
