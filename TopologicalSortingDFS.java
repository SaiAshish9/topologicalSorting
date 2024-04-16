package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class TopologicalSortingDFS {

    void dfs(Node node, boolean[] visited, Stack<Integer> stack) {
        visited[node.val] = true;
        for (Node x : node.neighbors) {
            if (!visited[x.val]) {
                dfs(x, visited, stack);
            }
        }
        stack.push(node.val);
    }

    List<Integer> process(List<Node> nodes) {
        Stack<Integer> stack = new Stack<>();
        int n = nodes.size();
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(nodes.get(i), visited, stack);
            }
        }

        Collections.reverse(stack);
        return stack;
    }

    public static void main(String[] args) {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node2.neighbors.addAll(Arrays.asList(node3));
        node3.neighbors.addAll(Arrays.asList(node1));
        node4.neighbors.addAll(Arrays.asList(node0, node1));
        node5.neighbors.addAll(Arrays.asList(node2, node0));

        List<Node> nodes = new ArrayList<>();
        nodes.addAll(Arrays.asList(node0, node1, node2, node3, node4, node5));

        List<Integer> result = new KahnBFS().process(nodes);
        System.out.println(result);
//        [4, 5, 2, 0, 3, 1]
    }


}
