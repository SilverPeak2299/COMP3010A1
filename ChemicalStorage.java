import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class ChemicalStorage {
    int[][] adjMatrix;
    ArrayList<ArrayList<Integer>> incompatibleGroups;
    int noOfChem;

    ChemicalStorage(int noOfChem) {
        this.noOfChem = noOfChem;
        adjMatrix = new int[noOfChem][noOfChem]; // Default value of inilaization is 0
        incompatibleGroups = new ArrayList<>();
    }

    void ReadInputs(Scanner sc,int noOfGroups) {
        for (int i = 0; i < noOfGroups; i++) {
            ArrayList<Integer> group = new ArrayList<>();

            // generating groups
            for (int chem = sc.nextInt(); chem != 0; chem = sc.nextInt()) {
                group.add(chem - 1); // zero indexing the chemical for easier manipulation of the adj matrix
            }
            
            // Filling in adjaceny matrix
            for (int chem1 : group) {
                for (int chem2 : group) {
                    if (chem1 != chem2) {
                        adjMatrix[chem1][chem2] = 1;
                        adjMatrix[chem2][chem1] = 1;
                    }
                }
            }

            incompatibleGroups.add(group);
        }
    }

    public boolean compatible(int chem1, int chem2){
        return adjMatrix[chem1][chem2] != 1;
    }

    public int labelCount(HashMap<Integer, Integer> roomAllocation, int noOfRooms) {
        int labels = 0;

        for (ArrayList<Integer> group : incompatibleGroups) {
            boolean groupInRoom = true;
            int roomOfFirstChem = group.get(0);

            for (int chem = 0; chem < group.size(); chem++) {
                if(roomAllocation.get(group.get(chem)) != roomOfFirstChem)
                    groupInRoom = false;
            }
            
            if (groupInRoom)
                labels++;
        }

        return labels;
    }
}
