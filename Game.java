
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Game {


    /**The method checks if the game state provided can be solved
     * @param puzzle the game state to be solve */
    public static boolean isGameSolvable(byte[] puzzle){
        int inversion = 0; // A pair of tiles form an inversion if the values on tiles are in reverse order of their appearance in goal state. 
        for(int i = 0 ; i < puzzle.length ; i++){
            if(puzzle[i] == 0) continue;
            for(int j = i+1 ; j < puzzle.length ; j++){
                if(puzzle[j] != 0 && puzzle[i] > puzzle[j]) inversion++;
            }
        }
        return (inversion % 2 == 0);
    }
    


    /**Method to generate random board and checks if it is solvable */
    public static byte[] generateRandomBoard() {
        
        byte[] puzzle = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int i = 0;
        while(i<9) {
            byte rNum = randomNum();
            
            if (!Contains(puzzle, rNum)){
                puzzle[i] = rNum;
                i++;
            }
        }
        if(isGameSolvable(puzzle)) return puzzle;
        else return generateRandomBoard();
    }

    private static byte randomNum() {
        byte num = (byte) (Math.random() * 9);
        return num;
    }

    private static boolean Contains(byte[] arr, byte target) {
        for (byte item : arr) {
            if (item == target) return true;
        }
        return false;
    }

    public static void main(String[] args) {

        byte[] puzzle = generateRandomBoard();

        Scanner entry = new Scanner(System.in);
        System.out.print("Do you want to solve your own board? (Yes/no): ");
        String option = entry.nextLine();

        
        
        if (option.toLowerCase().equals("yes")) {
            System.out.print("Enter the board state e.g(1,2,4,5,3,7,...): ");
            String board = entry.nextLine();
            
            String[] boardArr = board.split(",");
            if (boardArr.length != 9) {
                System.out.println("The board is invalid");
                puzzle = generateRandomBoard();
            } else {
                for (int i = 0; i < 9; i++) {

                    puzzle[i] = (byte) Integer.parseInt(boardArr[i].trim());

                }
                if (!isGameSolvable(puzzle)) {
                    System.out.println("The board is not solvable");
                    puzzle = generateRandomBoard();
                }
            }
        }
        System.out.println("\nThe board is ");
        
        
        
        Node root = new Node(puzzle);
        root.printPuzzle();
        Search algorithm = new Search();

        long startTime = System.nanoTime();
        List<Node> solution = algorithm.breadthFirstSearch(root); 
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);
        

        if (solution.size() > 0) {
            for (int i = solution.size() - 1; i >= 0 ; i--) {
                solution.get(i).printPuzzle();
            }
        }
        else {
            System.out.println("No path to solution found");
        }
        
    }
    
}
