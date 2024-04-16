package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class RankTransformOfAMatrix_13_1632 {

//    Union find nodes and assign ranks with topological sort
    public int[][] matrixRankTransform(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] rank = new int[m + n];
        int result[][] = new int[m][n];
        Map<Integer, List<int[]>> valueToCells = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int value = matrix[i][j];
                if (!valueToCells.containsKey(value))
                    valueToCells.put(value, new ArrayList<>());
                valueToCells.get(value).add(new int[]{i, j});
            }
        }

        for (int value : valueToCells.keySet()) {
            List<int[]> cells = valueToCells.get(value);
            Map<Integer, List<int[]>> rowMap = new HashMap<>(), colMap = new HashMap<>();
            for (int[] cell : cells) {
                int row = cell[0], col = cell[1];
                rowMap.computeIfAbsent(row, k -> new ArrayList<>()).add(cell);
                colMap.computeIfAbsent(col, k -> new ArrayList<>()).add(cell);
            }
            for (int row : rowMap.keySet())
                addEdges(graph, rowMap.get(row), n);
            for (int col : colMap.keySet())
                addEdges(graph, colMap.get(col), n);
        }

        List<Integer> sortedOrder = topologicalSort(graph, m + n);

        for (int cellIndex : sortedOrder) {
            int row = cellIndex / n, col = cellIndex % n, rankValue = 0;
            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int newRow = row + dir[0], newCol = col + dir[1];
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n)
                    rankValue = Math.max(rankValue, rank[newRow * n + newCol]);
            }
            result[row][col] = rank[cellIndex] = rankValue + 1;
        }

        return result;
    }

    private void addEdges(Map<Integer, List<Integer>> graph, List<int[]> cells, int n) {
        if (cells.size() > 1) {
            for (int i = 1; i < cells.size(); i++) {
                int cell1 = cells.get(i - 1)[0] * n + cells.get(i - 1)[1];
                int cell2 = cells.get(i)[0] * n + cells.get(i)[1];
                graph.computeIfAbsent(cell1, k -> new ArrayList<>()).add(cell2);
            }
        }
    }

    private List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, int numVertices) {
        List<Integer> sortedOrder = new ArrayList<>();
        int[] inDegree = new int[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        for (int node : graph.keySet())
            for (int neighbor : graph.get(node))
                inDegree[neighbor]++;

        for (int i = 0; i < numVertices; i++)
            if (inDegree[i] == 0)
                queue.offer(i);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            sortedOrder.add(node);
            if (graph.containsKey(node))
                for (int neighbor : graph.get(node))
                    if (--inDegree[neighbor] == 0)
                        queue.offer(neighbor);
        }

        return sortedOrder;
    }

    public static void main(String[] args) {
        System.out.println(new RankTransformOfAMatrix_13_1632().matrixRankTransform(
                new int[][]{
                        {1,2},
                        {3,4}
                }
        ));
        System.out.println();
    }

}


//    Traverse the matrix and build a map to store the value and corresponding list of cell coordinates.
//        Initialize an array to keep track of the rank of each cell, initially set to 0.
//        Create a directed graph where nodes represent cells and edges represent dependencies between cells based on their values.
//        Perform a topological sort on the graph to determine the order in which cells should be processed.
//        Traverse the sorted order and assign ranks to cells based on the maximum rank of adjacent cells plus one.
//        Update the rank array with the assigned ranks for each cell.
//        Populate the result matrix with the ranks obtained from the rank array.