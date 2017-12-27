//******************************************************************************
// CLASS: Main (main.java)
//
// DESCRIPTION
// Imports a given series of Integers, and then counts the number of runs
// (sequences of increasing or decreasing integers) in the file.
//
// COURSE AND PROJECT INFO
// CSE205 Object Oriented Programming and Data Structures, Summer 2016
// Project Number: 1
//
// AUTHOR
// Vijay Ramakrishna (Vijay.Ramakrishna@asu.edu)
//******************************************************************************
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author VRama
 */
public class Main {

    public static void main(String[] pArgs) {
        Main mainObject = new Main();
        mainObject.run();
    }

    /**
     * Runs the full program by calling the other methods
     */
    private void run() {
        ArrayList<Integer> list = ReadFile("p01-in.txt");
        ArrayList<Integer> listRunsUpCount = FindRuns(list, "RUNS_UP");
        ArrayList<Integer> listRunsDnCount = FindRuns(list, "RUNS_DN");
        ArrayList<Integer> listRunsCount = Merge(listRunsUpCount, listRunsDnCount);
        Output("p01-runs.txt", listRunsCount);
    }

    /**
     * Reads the input file
     */
    private ArrayList<Integer> ReadFile(String inputFilename) {
        ArrayList<Integer> scan = new ArrayList<Integer>();
        try {
            Scanner in = new Scanner(new File(inputFilename));
            while (in.hasNext()) {
                scan.add(in.nextInt());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Error: Input File Not Found");
            System.exit(-1);
        }
        return scan;
    }

    /**
     * Finds the number of runs in a given direction (either increasing or
     * decreasing)
     */
    private ArrayList<Integer> FindRuns(ArrayList<Integer> list, String dir) {
        ArrayList<Integer> listRunsCount = arrayListCreate(list.size(), 0);
        int i = 0;
        int k = 0;
        while (i < list.size() - 1) {
            if ("RUNS_UP".equals(dir) && (list.get(i) <= list.get(i + 1))) {
                k++;
            } else if ("RUNS_DN".equals(dir) && (list.get(i) >= list.get(i + 1))) {
                k++;
            } else if (k != 0) {
                Integer value = listRunsCount.get(k);
                value = value + 1;
                listRunsCount.set(k, value);
                k = 0;
            }
            i++;
        }
        if (k != 0) {
            Integer value = listRunsCount.get(k);
            value = value + 1;
            listRunsCount.set(k, value);
        }
        return listRunsCount;
    }

    /**
     * Finds the total number of runs by totalling the total number of runs of
     * each given size from each of the two directions (Increasing and
     * Decreasing)
     */
    private ArrayList<Integer> Merge(ArrayList<Integer> listRunsUpCount, ArrayList<Integer> listRunsDownCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(listRunsUpCount.size(), 0);
        for (int i = 0; i < (listRunsUpCount.size() - 1); i++) {
            Integer upCount = listRunsUpCount.get(i);
            Integer dnCount = listRunsDownCount.get(i);
            Integer totalCount = upCount + dnCount;
            listRunsCount.set(i, totalCount);

        }
        return listRunsCount;
    }

    /**
     * Creates a new ArrayList
     */
    private ArrayList<Integer> arrayListCreate(int size, int initValue) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= size; i++) {
            list.add(initValue);
        }
        return list;
    }

    /**
     * Creates output file and writes the total number and size of runs to it by
     * summarizing ArrayList details
     */
    private void Output(String Filename, ArrayList<Integer> ListRuns) {
        PrintWriter out = null;
        try {
            Integer sum = 0;
            for (int i = 0; i <= (ListRuns.size() - 1); i++) {
                Integer current = ListRuns.get(i);
                sum = sum + current;
            }
            out = new PrintWriter(new File(Filename));
            out.println("runs_total, " + sum);
            for (int l = 1; l <= (ListRuns.size() - 1); l++) {
                Integer value = ListRuns.get(l);
                out.println("runs_" + l + ", " + value);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Error: Output File Not Found");
            System.exit(-1);
        } finally {
            out.close();
        }
    }
}
