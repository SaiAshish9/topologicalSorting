package com.sai.designPatterns.topologicalSorting;
import java.util.*;

public class CountWaysToBuildRoomsInAnAntColony_17_1996 {
    private static final long MOD = (long)1e9 + 7;

    private int[] sub;
    private List<List<Integer>> adj;

    public int waysToBuildRooms(int[] prevRoom) {
        int n = prevRoom.length;
        sub = new int[n];
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        int[] in = new int[n];
        for (int i = 1; i < n; i++) {
            adj.get(prevRoom[i]).add(i);
            in[i]++;
        }
        dfs(0);
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (in[i] == 0) q.offer(i);
        }
        while (!q.isEmpty()) {
            int t = q.poll();
            topo.add(t);
            for (int i : adj.get(t)) {
                in[i]--;
                if (in[i] == 0) q.offer(i);
            }
        }
        long[] ans = new long[n];
        Arrays.fill(ans, 1);
        for (int j = n - 2; j >= 0; j--) {
            ans[j] *= (n - j);
            ans[j] %= MOD;
            ans[j] *= ans[j + 1];
            ans[j] %= MOD;
            ans[j] *= powm(sub[topo.get(j)], MOD - 2);
            ans[j] %= MOD;
        }
        return (int)ans[0];
    }

    private void dfs(int i) {
        for (int j : adj.get(i)) {
            dfs(j);
            sub[i] += sub[j];
        }
        sub[i]++;
    }

    private long powm(long a, long b) {
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans *= a;
                ans %= MOD;
            }
            a *= a;
            a %= MOD;
            b >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        CountWaysToBuildRoomsInAnAntColony_17_1996 solution = new CountWaysToBuildRoomsInAnAntColony_17_1996();
        int[] p = {-1, 0, 1, 2};
        int result = solution.waysToBuildRooms(p);
        System.out.println(result);
    }
}
