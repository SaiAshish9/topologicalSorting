package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class SortItemsByGroupsRespectingDependencies_16_1632 {

    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        List<Integer> groupItems[] = new List[m + n];
        for (int i = 0; i < group.length; i++) {
            if (group[i] == -1) group[i] = m + i;
            if (groupItems[group[i]] == null) groupItems[group[i]] = new ArrayList<>();
            groupItems[group[i]].add(i);
        }

        List<Integer> groupGraph[] = new List[m + n];
        int groupInDegree[] = new int[m + n];
        List<Integer> itemGraph[] = new List[n];
        int itemInDegree[] = new int[n];

        for (int i = 0; i < beforeItems.size(); i++) {
            int currentGroup = group[i];
            for (int before : beforeItems.get(i)) {
                int beforeGroup = group[before];
                if (beforeGroup != currentGroup) {
                    if (groupGraph[beforeGroup] == null) groupGraph[beforeGroup] = new ArrayList<>();
                    groupGraph[beforeGroup].add(currentGroup);
                    groupInDegree[currentGroup]++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int before : beforeItems.get(i)) {
                if (group[i] == group[before]) {
                    if (itemGraph[before] == null) itemGraph[before] = new ArrayList<>();
                    itemGraph[before].add(i);
                    itemInDegree[i]++;
                }
            }
        }

        List<Integer> groupOrder = topologicalSort(groupGraph, groupInDegree);
        List<Integer> itemOrder = topologicalSort(itemGraph, itemInDegree);

        if (groupOrder.size() != m + n || itemOrder.size() != n) return new int[0];

        Map<Integer, List<Integer>> groupToItems = new HashMap<>();
        for (int i : itemOrder) {
            if (!groupToItems.containsKey(group[i])) groupToItems.put(group[i], new ArrayList<>());
            groupToItems.get(group[i]).add(i);
        }

        int result[] = new int[n];
        int index = 0;
        for (int groupIndex : groupOrder) {
            if (groupItems[groupIndex] != null) {
                for (int item : groupToItems.getOrDefault(groupIndex, new ArrayList<>())) {
                    result[index++] = item;
                }
            }
        }

        return result;
    }

    private List<Integer> topologicalSort(List<Integer> graph[], int inDegree[]) {
        List<Integer> sortedOrder = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < graph.length; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            sortedOrder.add(currentNode);
            if (graph[currentNode] != null) {
                for (int neighbor : graph[currentNode]) {
                    if (--inDegree[neighbor] == 0) queue.offer(neighbor);
                }
            }
        }

        return sortedOrder.size() == graph.length ? sortedOrder : new ArrayList<>();
    }

    public static void main(String[] args) {
        SortItemsByGroupsRespectingDependencies_16_1632 solution = new SortItemsByGroupsRespectingDependencies_16_1632();
        int n = 8;
        int m = 2;
        int[] group = new int[]{-1, -1, 1, 0, 0, 1, 0, -1};
        List<List<Integer>> beforeItems = new ArrayList<>();
        beforeItems.add(Arrays.asList(6));
        beforeItems.add(Arrays.asList(5));
        beforeItems.add(Arrays.asList());
        beforeItems.add(Arrays.asList(6));
        beforeItems.add(Arrays.asList(3, 6));
        beforeItems.add(Arrays.asList());
        beforeItems.add(Arrays.asList());
        beforeItems.add(Arrays.asList());
        int[] result = solution.sortItems(n, m, group, beforeItems);
        System.out.println(Arrays.toString(result));
    }

}

