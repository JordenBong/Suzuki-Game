package com.Suzume;

import java.text.Normalizer;
import java.util.*;
// USING BREADTH-FIRST SEARCH ALGORITHM
public class PossiblePaths {
    public static int x = 9;
    private static final int ROW = 40, COLUMN = 20;

    // to store the possible paths
    public static List<List<Integer>> possiblePaths = new ArrayList<>();

    // to store directions for the successful paths
    public static List<List<ArrayList<Integer>>> directionsAllPaths = new ArrayList<>();

    public static void main(String[] args) {
        FormMap.formCompleteMap();

        // change 3 to 1 in 3 map pieces
         FormMap.getCompleteMap()[19][9] = 1;  // pixel 1
         FormMap.getCompleteMap()[19][19] = 1; // pixel 2
        FormMap.getCompleteMap()[39][9] = 1;  // pixel 3

        System.out.println("Ultimate Map");
        FormMap.displayMap();
        System.out.print("Number of possible paths in complete map: ");
        findPaths(FormMap.getCompleteMap());
    }



    public static void findPaths(int[][] array) {
        int goalNode = 3, countLoop = 1;

        // queue that keeps track of the visited index path of all nodes
        Queue<List<ArrayList<Integer>>> visitedIndexQueue = new LinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(0);
        List<ArrayList<Integer>> innerList = new LinkedList<>();
        innerList.add(arrayList);
        visitedIndexQueue.offer(innerList);

        // queue for moving node to next node
        Node startNode = new Node(0, 0);
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.offer(startNode);

        // queue that stores path
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.offer(Arrays.asList(array[0][0]));

        // to store the result
        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            // current node to process
            Node node = nodeQueue.poll();

            // visited index path up to current node ( in 2D array index form, [0,0], [0,1], etc.)
            List<ArrayList<Integer>> visitedIndex = visitedIndexQueue.poll();

            // visited path up to current node ( in integer form, 0, 2, etc.)
            List<Integer> path = queue.poll();

            int lastNode = path.get(path.size() - 1);

            // if lastNode = 3, then store in result
            if (lastNode == goalNode) {
                // for the current BFS algorithm usage
                result.add(new ArrayList<>(path));

                // to use in class ShortestPath to find shortest paths
                possiblePaths.add(new ArrayList<>(path));
                // to use in class ShortestPath to trace the direction of shortestPath
                directionsAllPaths.add(new ArrayList<ArrayList<Integer>>(visitedIndex));

            }
            // else continue exploring other directions of the paths
            else {

                //right left down up
                int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

                //check for all 4 directions
                for (int i = 0; i < 4; i++) {

                    int frequencyOf2 = 0;

                    // holder to update the node path for each possible move
                    ArrayList<Integer> pathUpdater = new ArrayList<>(path);

                    // holder to update the index path for each possible move
                    ArrayList<ArrayList<Integer>> visitedIndexUpdater = new ArrayList<>(visitedIndex);

                    int row = node.x + direction[i][0];
                    int column = node.y + direction[i][1];

                    // first loop does not have any previous node index to compare to
                    if (countLoop == 1 && row >= 0 && column >= 0 && row < ROW && column < COLUMN && array[row][column] != 1) {

                        // add the path for valid direction that has been traversed
                        pathUpdater.add(array[row][column]);
                        queue.offer(pathUpdater);

                        // add the next valid node
                        nodeQueue.offer(new Node(row, column));

                        // mark the current node as visited in index list
                        ArrayList<Integer> update = new ArrayList<>();
                        update.add(node.x);
                        update.add(node.y);
                        visitedIndexUpdater.add(update);

                        visitedIndexQueue.offer(visitedIndexUpdater);
                    }

                    // second loop onwards need to compare with visited node index path
                    else if (countLoop != 1 && row >= 0 && column >= 0 && row < ROW && column < COLUMN && array[row][column] != 1) {

                        // check the index of node to be added
                        ArrayList<Integer> checker = new ArrayList<>();
                        checker.add(row);
                        checker.add(column);

                        // to ensure the node to be added has not been visited
                       if (!visitedIndex.contains(checker)) {

                           // check the frequency of 'stations' visited of current path
                          for(Integer e: path){
                              if(e == 2)
                                  frequencyOf2 += 1;
                          }
                           // if more than 4 stations has been visited, just drop the path
                           // and continue exploring other paths, which is other direction
                          if(frequencyOf2 > 4)
                               continue;

                          // same as previous
                           pathUpdater.add(array[row][column]);
                           queue.offer(pathUpdater);

                           nodeQueue.offer(new Node(row, column));

                           ArrayList<Integer> update = new ArrayList<>();
                           update.add(node.x);
                           update.add(node.y);
                           visitedIndexUpdater.add(update);

                           visitedIndexQueue.offer(visitedIndexUpdater);
                        }
                    }
                }
            }
            countLoop += 1;

        } // end while

        int path = 0;

        // to check for the path that visited exactly 4 stations
        for (List<Integer> inner : result) {
            // reset frequency back to 0 for each inner list
            int frequency = 0;
            for (int element : inner) {
                if (element == 2)
                    frequency += 1;
            }
            if (frequency == 4)
                path += 1;
        }
        System.out.println(path);
    } // end method
}

