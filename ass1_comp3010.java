import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;

public class ass1_comp3010 {
    static Scanner scanner;

    static int noOfChem;
    static int noOfRooms;
    static int noOfGroups;

    static ChemicalStorage chemStorage;
    

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        System.out.println("Enter the number of chemicals to manage:");
        noOfChem = scanner.nextInt();

        System.out.println("Enter the number of storage rooms:");
        noOfRooms = scanner.nextInt();

        System.out.println("Enter the number of groups of incompatible chemicals you must separate:");
        noOfGroups = scanner.nextInt();

        System.out.println("Enter the list of incompatible chemicals of each group (one group per line, each terminated by 0):");
        chemStorage = new ChemicalStorage(noOfChem);
        chemStorage.ReadInputs(scanner, noOfGroups);

        HashMap<Integer, Integer> roomAllocoation = roomAssignment();


        System.out.println("\nThe number of required labels and the different room allocations are:");
        System.out.println(chemStorage.labelCount(roomAllocoation, noOfRooms));
        printAssignment(roomAllocoation);
    }

    // Using a greedy algorithim to assign chemicals to rooms
    public static HashMap<Integer, Integer> roomAssignment() {
        HashMap<Integer, Integer> roomAllocation = new HashMap<>();
        
        // Assign first chem to first room
        roomAllocation.put(0, 0);

        boolean[] availableRoom = new boolean[noOfRooms];
        Arrays.fill(availableRoom, true);

        // Assinging rooms to remaining chemicals
        for (int chem1 = 1; chem1 < noOfChem; chem1++) {

            // Identfying Conflicting Rooms
            for (int chem2 = 0; chem2 < noOfChem; chem2++) {
                if (!chemStorage.compatible(chem1, chem2) && roomAllocation.containsKey(chem2))
                    availableRoom[roomAllocation.get(chem2)] = false;
                
            }

            // Allocating Fist available room
            int currRoom = 0;
            for (boolean room: availableRoom) {
                if (room)
                    break;

                currRoom++;
            }

            roomAllocation.put(chem1, currRoom % noOfRooms);
            Arrays.fill(availableRoom, true);
        }

        return roomAllocation;
    }


    public static void printAssignment(HashMap<Integer, Integer> roomAllocation){
        for (int room = 0; room < noOfRooms; room++) {
            for (int chem : roomAllocation.keySet()) {
                if (roomAllocation.get(chem) == room)
                    System.out.print(chem+1 + " ");
            }

            System.out.println("0");
        }
    }
}
