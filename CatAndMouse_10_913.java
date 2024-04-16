package com.sai.designPatterns.topologicalSorting;

import java.util.*;

public class CatAndMouse_10_913 {

    public static final int HOLE = 0, MOUSE_START = 1, CAT_START = 2;
    public static final int MOUSE_TURN = 0, CAT_TURN = 1;
    public static final int MOUSE_WIN = 1, CAT_WIN = 2, UNKNOWN = 0;

    public int catMouseGame(int[][] graph) {

        int n = graph.length;
        int[][][] degrees = new int[n][n][2];
        int[][][] results = new int[n][n][2];
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                degrees[i][j][MOUSE_TURN] = graph[i].length;
                degrees[i][j][CAT_TURN] = graph[j].length;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j : graph[HOLE]) {
                degrees[i][j][CAT_TURN]--;
            }
        }

        for (int i = 1; i < n; i++) {
            results[i][i][MOUSE_TURN] = CAT_WIN;
            results[i][i][CAT_TURN] = CAT_WIN;
            queue.offer(new int[]{i, i, MOUSE_TURN});
            queue.offer(new int[]{i, i, CAT_TURN});
        }

        for (int j = 1; j < n; j++) {
            results[0][j][MOUSE_TURN] = MOUSE_WIN;
            results[0][j][CAT_TURN] = MOUSE_WIN;
            queue.offer(new int[]{0, j, MOUSE_TURN});
            queue.offer(new int[]{0, j, CAT_TURN});
        }

        while (!queue.isEmpty()) {
            int[] state = queue.poll();
            int Mouse = state[0], Cat = state[1], turn = state[2];
            int result = results[Mouse][Cat][turn];
            List<int[]> prevStates = getPrevStates(Mouse, Cat, turn, graph);

            for (int[] prevState : prevStates) {
                int prevMouse = prevState[0], prevCat = prevState[1], prevTurn = prevState[2];

                if (results[prevMouse][prevCat][prevTurn] == UNKNOWN) {
                    boolean winState = (result == MOUSE_WIN && prevTurn == MOUSE_TURN)
                            || (result == CAT_WIN && prevTurn == CAT_TURN);

                    if (winState) {
                        results[prevMouse][prevCat][prevTurn] = result;
                        queue.offer(new int[]{prevMouse, prevCat, prevTurn});
                    } else {
                        degrees[prevMouse][prevCat][prevTurn]--;

                        if (degrees[prevMouse][prevCat][prevTurn] == 0) {
                            results[prevMouse][prevCat][prevTurn] = result;
                            queue.offer(new int[]{prevMouse, prevCat, prevTurn});
                        }
                    }
                }
            }
        }

        return results[MOUSE_START][CAT_START][MOUSE_TURN];
    }

    private List<int[]> getPrevStates(int Mouse, int Cat, int turn, int[][] graph) {
        List<int[]> prevStates = new ArrayList<>();
        int prevTurn = turn == MOUSE_TURN ? CAT_TURN : MOUSE_TURN;

        if (prevTurn == CAT_TURN) {
            for (int prevCat : graph[Cat]) {
                if (prevCat != HOLE) {
                    prevStates.add(new int[]{Mouse, prevCat, prevTurn});
                }
            }
        } else {
            for (int prevMouse : graph[Mouse]) {
                prevStates.add(new int[]{prevMouse, Cat, prevTurn});
            }
        }

        return prevStates;
    }

    public static void main(String[] args) {
        CatAndMouse_10_913 solution = new CatAndMouse_10_913();
        int[][] graph = {{2, 5}, {3}, {0, 4, 5}, {1, 4, 5}, {2, 3}, {0, 2, 3}};
        int result = solution.catMouseGame(graph);
        System.out.println(result);
    }

}
