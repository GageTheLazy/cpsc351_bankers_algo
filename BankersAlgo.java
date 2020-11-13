// Gage Dimapindan, CWID: 888017746
// Banker's Algorithm
// CPSC 351-01, CSUF Fall 2020

import java.io.*;
import java.util.*;

public class BankersAlgo {

    private int need[][];
    private int allocate[][];
    private int max[][];
    private int avail[][];

    private int numProc; // number of processes
    private int numRes; // number of resources

    private void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes and resources: ");
        numProc = sc.nextInt();  //no. of processes
        numRes = sc.nextInt();  //no. of resources

        need = new int[numProc][numRes];  //initializing arrays
        max = new int[numProc][numRes];
        allocate = new int[numProc][numRes];
        avail = new int[1][numRes];

        System.out.println("Enter allocation matrix: ");
        for (int i = 0; i < numProc; i++) {
            for (int j = 0; j < numRes; j++) {
                allocate[i][j] = sc.nextInt();  //allocation matrix
            }
        }

        System.out.println("Enter max. matrix: ");
        for (int i = 0; i < numProc; i++) {
            for (int j = 0; j < numRes; j++) {
                max[i][j] = sc.nextInt();  //max matrix
            }
        }
        System.out.println("Enter available matrix: ");
        for (int j = 0; j < numRes; j++) {
            avail[0][j] = sc.nextInt();  //available matrix
        }
        sc.close();
    }

    //calculate the need matrix
    private int[][] calc_need() {

        for(int i = 0; i < numProc; i++) {
            for(int j = 0; j < numRes; j++) {  //calculating need matrix
                need[i][j] = max[i][j] - allocate[i][j];
            }
        }
        return need;
    }

    //Check if the requested resource is available or not
    private boolean check(int i) {
        for (int j = 0; j < numRes; j++) {
            if (avail[0][j] < need[i][j]) {
                return false;
            }
        }
        return true;
    }


    // Check if by fulfilling the resource request the system remains in safe state
    public void isSafe() {
        input(); //collecting data from the user
        calc_need(); //applying calculations
        boolean done[] = new boolean[numProc];
        int j = 0;

        while (j < numProc) {  //loop until all process allocated
            boolean allocated = false;
            for (int i = 0; i < numProc; i++) {
                if (!done[i] && check(i)) {  //trying to allocate
                    for (int k = 0; k < numRes; k++) {
                        avail[0][k] = avail[0][k] - need[i][k] + max[i][k];
                    }
                    System.out.println("Allocated process : " + i);
                    allocated = done[i] = true;
                    j++;
                }
            }
            if(!allocated) {
                break; // if there's no allocation
            }
        }
        if (j == numProc) {  //if all processes are allocated
            System.out.println("\nSafely allocated!");
        }
        else {
            System.err.println("\nAll proceess can't be allocated safely!");
        }
    }

    public static void main(String[] args) throws IOException {
        new BankersAlgo().isSafe();
    }
}