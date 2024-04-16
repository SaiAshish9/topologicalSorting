package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class CourseSchedule_2_210 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Node> nodes = new ArrayList<>();
        int visitedCount = 0;
        int[] in_degree = new int[numCourses];
        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            nodes.add(new Node(i));
        }

        for (int i = 0; i < numCourses; i++) {
            int course = prerequisites[i][0];
            int prerequisite = prerequisites[i][1];
            nodes.get(prerequisite).neighbors.add(new Node(course));
            in_degree[course]++;
        }

        for (int i = 0; i < numCourses; i++) {
            if(in_degree[i] == 0){
                queue.add(nodes.get(i).val);
            }
        }

        while (!queue.isEmpty()){
            int val = queue.poll();
            visitedCount++;
            result.add(val);
            for(Node node: nodes.get(val).neighbors){
                if(--in_degree[node.val]==0){
                    queue.add(node.val);
                }
            }
        }

        if (visitedCount == numCourses) {
            return result.stream().mapToInt(Integer::intValue).toArray();
        }
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CourseSchedule_2_210().findOrder(2, new int[][]{
                {1, 0}
        })));
    }
}
