package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class FindEventualSafeStates_8_802 {

    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> eventualSafeNodes = new ArrayList<>();

        // Create the reverse graph
        List<List<Integer>> reverseGraph = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            reverseGraph.add(new ArrayList<>());
        }
        int[] inDegree = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j : graph[i]) {
                reverseGraph.get(j).add(i);
                inDegree[i]++;
            }
        }

        // Topological sorting
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int node = queue.poll();
            eventualSafeNodes.add(node);
            for (int neighbor : reverseGraph.get(node)) {
                if (--inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        Collections.sort(eventualSafeNodes);
        return eventualSafeNodes;
    }

    public static void main(String[] args) {
        int[][] graph = {{1,2},{2,3},{5},{0},{5},{},{}};
        List<Integer> result = new FindEventualSafeStates_8_802().eventualSafeNodes(graph);
        System.out.println(result);
    }

//    In this solution, we first create the reverse graph and calculate the in-degree of each node.
//    Then, we perform topological sorting using a queue. After that, we collect all nodes with
//    in-degree 0 (which are the eventual safe nodes) and return them.

}
