package com.sai.designPatterns.topologicalSorting;

import java.util.LinkedList;
import java.util.Queue;

public class LongestIncreasingPathInAMatrix_5 {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] in_degree = new int[m][n];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int longestPath = 0;

        // Calculate in_degrees for each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : directions) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] > matrix[i][j]) {
//                        this condition helps to check whether we can reach cell i, j or not
                        in_degree[i][j]++;
                    }
                }
            }
        }

        // Perform topological sort
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (in_degree[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            longestPath++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] node = queue.poll();
                int x = node[0];
                int y = node[1];
                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && matrix[newX][newY] > matrix[x][y]) {
                        if (--in_degree[newX][newY] == 0) {
                            queue.offer(new int[]{newX, newY});
                        }
                    }
                }
            }
        }

        return longestPath;
    }

    public static void main(String[] args) {
        LongestIncreasingPathInAMatrix_5 lip = new LongestIncreasingPathInAMatrix_5();
        int[][] matrix = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        System.out.println("Longest increasing path: " + lip.longestIncreasingPath(matrix)); // Output: 4
//        The longest increasing path is [1, 2, 6, 9].
    }
}
