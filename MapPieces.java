package com.Suzume;
import java.util.*;
// USING DEPTH-FIRST SEARCH ALGORITHM
public class MapPieces {
    private static final int ROW = 20, COLUMN = 10;
    public static void main(String[] args) {
        Pixel pixel = new Pixel();
        int[][] pixel1 = pixel.image1.getArray();
        int[][] pixel2 = pixel.image2.getArray();
        int[][] pixel3 = pixel.image3.getArray();
        int[][] pixel4 = pixel.image4.getArray();
        System.out.print("Image 1: ");
        dfs(pixel1);
        System.out.print("Image 2: ");
       // dfs(pixel2);
        System.out.print("Image 3: ");
      //  dfs(pixel3);
        System.out.print("Image 4: ");
      //  dfs(pixel4);
        int[][] test = {{0, 0, 2, 0, 0, 0, 1, 1, 1, 1},
                        {0, 1, 0, 1, 1, 0, 0, 0, 1, 1},
                        {0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
                        {0, 1, 0, 1, 1, 0, 0, 2, 0, 1},
                        {0, 1, 0, 1, 1, 0, 0, 1, 0, 3 }};
        System.out.println();
        //dfs(test);
    }

    public static void dfs(int[][] array){
        int goalNode = 3;

        // stack that stores current path
        Stack<Integer> currentPath = new Stack<>();
        currentPath.push(array[0][0]);

        // stack for moving node to next node / keep track of current node being referred to
        Node startNode = new Node(0,0);
        Stack<Node> currentNode = new Stack<>();
        currentNode.push(startNode);

        // stack that keeps track of the visited node index path
        Stack<List<ArrayList<Integer>>> visitedIndexStack = new Stack<>();

        // main stack to determine when to stop the iteration / adjacent nodes of current node
        Stack<Node> adjacentNode = new Stack<>();

        // TO INITIALIZE adjacentNode stack & visitedIndex stack
        // right left down up
        int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        // check for all 4 directions
        for(int i=0;i<4;i++){
            int row = startNode.x + direction[i][0];
            int column = startNode.y + direction[i][1];
            if(row >= 0 && column >= 0 && row < ROW && column < COLUMN && array[row][column] != 1){

                // add valid adjacent nodes of start node
                adjacentNode.push(new Node(row,column));

                // mark start node as visited for both above adjacent nodes
                ArrayList<Integer> list = new ArrayList<>();
                list.add(0);
                list.add(0);
                List<ArrayList<Integer>> innerList = new LinkedList<>();
                innerList.add(list);
                visitedIndexStack.push(innerList);
            }
        }

        // to store the result
        List<List<Integer>> result = new ArrayList<>();

        while(!adjacentNode.isEmpty()){
            System.out.println("node: "+adjacentNode);
            System.out.println("index "+visitedIndexStack);

            // current node to process
            Node node = adjacentNode.pop();

            // to keep track of the current node being referred to alongside with current path
            currentNode.push(node);

            // to keep track of the visited node index path of the current node
            List<ArrayList<Integer>> visitedIndex = visitedIndexStack.pop();

            // to update the currentPath relative to currentNode
            if(currentPath.isEmpty()){
                // backtracking to current node,
                // and add previous paths visited by current node based on visitedIndex
                // in backtracking, currentPath is cleared/empty,
                // to give way to exploring other paths
                 for(int i = 0;i<visitedIndex.size();i++){
                    currentPath.push(array[visitedIndex.get(i).get(0)][visitedIndex.get(i).get(1)]);
                }
                currentPath.push(array[node.x][node.y]);
            }
            else
                // just update the path if the currentPath is still exploring the path
                currentPath.push(array[node.x][node.y]);


            int lastNode = currentPath.peek();

            //if lastNode = 3, then store in result
            if (lastNode == goalNode) {
                ArrayList<Integer> copiedList = new ArrayList<>(currentPath);
                result.add(copiedList);

                // if path is found, the currentPath stack and currentNode stack
                // are cleared to give way to exploring other paths
                // basically is BACKTRACKING
                currentPath.clear();
                currentNode.clear();

                // skip to next iteration of while loop to check for remaining nodes
                // in adjacentNode stack
                continue;
            }

            // to verify the path has not come to a dead end
            boolean gotWays = false;

            for(int i=0;i<4;i++){

                // holder to update the index path for each possible move
                ArrayList<ArrayList<Integer>> visitedIndexUpdater = new ArrayList<>(visitedIndex);

                int row = node.x + direction[i][0];
                int column = node.y + direction[i][1];

                if(row >= 0 && column >= 0 && row < ROW && column < COLUMN && array[row][column] != 1){

                    // check the index of node to be added
                    ArrayList<Integer> checker = new ArrayList<>();
                    checker.add(row);
                    checker.add(column);

                    // to ensure the node to be added has not been visited
                    if(!visitedIndex.contains(checker)){

                        // means not a dead end, there is at least one direction to continue exploring
                        gotWays = true;

                        // same as previous
                        adjacentNode.push(new Node(row,column));

                        ArrayList<Integer> update = new ArrayList<>();
                        update.add(node.x);
                        update.add(node.y);
                        visitedIndexUpdater.add(update);
                        visitedIndexStack.push(visitedIndexUpdater);
                    }
                }
            }

            // if got ways to explore, continue exploring on current path
            if(gotWays)
                continue;

            // else means the path has come to a dead end, clear currentPath and currentNode
            // to embark on new possible paths
            else{
                currentPath.clear();
                currentNode.clear();
            }
        } // end while

        int path = 0;

        // to check for the path that visited exactly 3 stations
        for(List<Integer> inner:result){
            // reset frequency back to 0 for each inner list
            int frequency = 0;
            for(int element:inner){
                if(element==2)
                    frequency+=1;
            }
            if(frequency==3)
                path+=1;
        }

        System.out.println(path);

    }
}

class Node{
    int x;
    int y;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}


