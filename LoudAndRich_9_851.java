package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class LoudAndRich_9_851 {

    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        int[] inDegree = new int[n];
        for (int[] edge : richer) {
            int richerPerson = edge[1];
            int poorerPerson = edge[0];
            graph.get(poorerPerson).add(richerPerson);
            inDegree[richerPerson]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] result = new int[n];
        Arrays.fill(result, -1);
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }

        while (!queue.isEmpty()) {
            int person = queue.poll();
            for (int richerPerson : graph.get(person)) {
                if (quiet[result[richerPerson]] > quiet[result[person]]) {
                    result[richerPerson] = result[person];
                }
                if (--inDegree[richerPerson] == 0) {
                    queue.offer(richerPerson);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        LoudAndRich_9_851 solution = new LoudAndRich_9_851();
        int[][] richer = {{1,0},{2,1},{3,1},{3,7},{4,3},{5,3},{6,3}};
        int[] quiet = {3,2,5,4,6,1,7,0};
        int[] result = solution.loudAndRich(richer, quiet);
        System.out.println(Arrays.toString(result)); // Output: [5, 5, 5, 5, 6, 5, 7, 3]
    }
//    if person a is richer than person b, there's a directed edge from b to a.

}
