package com.Suzume;

import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ShortestPath{
    public static List<ArrayList<String>> directions = new ArrayList<>();

    public static void main(String[] args) {
        FormMap.formCompleteMap();

        // change 3 to 1 in 3 map pieces
        FormMap.getCompleteMap()[19][9] = 1;  // pixel 1
        FormMap.getCompleteMap()[19][19] = 1; // pixel 2
        FormMap.getCompleteMap()[39][9] = 1;  // pixel 3
//        CompleteMap ultimateMap = new CompleteMap();
//        ultimateMap.formCompleteMap();
//        // change 3 to 1 in 3 map pieces
//        ultimateMap.getCompleteMap()[19][9] = 1;  // pixel 1
//        ultimateMap.getCompleteMap()[19][19] = 1; // pixel 2
//        ultimateMap.getCompleteMap()[39][9] = 1;  // pixel 3
        System.out.print("Number of possible paths in complete map: ");
       // PossiblePaths.findPaths(FormMap.getCompleteMap());



        // TO FIND SHORTEST PATH(s)

        // to store the indices of shortest paths
        ArrayList<Integer> indices = new ArrayList<>();

        int min = Integer.MAX_VALUE, indexOfShortest = 0;

        for(List<Integer> list:PossiblePaths.possiblePaths){
            if(list.size()<=min){
                min = list.size();
                indices.add(indexOfShortest);
            }
            indexOfShortest += 1;

            // shortest paths are in the first few elements, once larger size is met, just break
            if(list.size()>min)
                break;
        }
        System.out.println("\nShortest path length: "+min+"\nNumber of shortest paths: "+indices.size()+"\n");

        for(int index:indices){
            System.out.print("Path "+(index+1)+" :");
            System.out.println(PossiblePaths.directionsAllPaths.get(index));
        }

        // TO FIND DIRECTIONS OF SHORTEST PATHS

        ArrayList<String> onePath = new ArrayList<>();

        for(int index:indices){
            for(int i = 1;i < PossiblePaths.directionsAllPaths.get(index).size()-1;i++) {
                if (PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(0)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(0) == 1
                        && PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(1)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(1) == 0
                ) {
                    onePath.add("Down");
                }
                else if (PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(0)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(0) == 0
                        && PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(1)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(1) == 1
                ) {
                    onePath.add("Right");
                }
                else if (PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(0)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(0) == -1
                        && PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(1)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(1) == 0
                ) {
                    onePath.add("Up");
                }
                else if (PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(0)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(0) == 0
                        && PossiblePaths.directionsAllPaths.get(index).get(i + 1).get(1)
                        - PossiblePaths.directionsAllPaths.get(index).get(i).get(1) == -1
                ) {
                    onePath.add("Left");
                }
            }

            // add "Right" for every path because all paths arrive at index (39,18)
            // due to design of algorithm, since (39,19) is 3
            // the path terminates at (39,18), does not update index (39,19) as visited path
            // one more step to the right would reach the destination
            onePath.add("Right");

            // create a new copy of the path before clear
            ArrayList<String> copyOnePath = new ArrayList<>(onePath);

            // store the directions of one path in the main arraylist
            // after each inner loop before starting next shortest path
            directions.add(copyOnePath);

            // clear the ArrayList for next shortest path
            onePath.clear();
        }


    for(int index:indices){
        System.out.println("Path " + (index + 1) + " :" + directions.get(index));
        System.out.println("");
        }
    }

}
