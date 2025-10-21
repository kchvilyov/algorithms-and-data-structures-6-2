package ru.t1.education;

import java.util.*;

public class TopologicalSort {

    public static List<Integer> topologicalSort(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        if (n == 0) return Collections.emptyList();

        // Список смежности
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Массив степеней захода для каждой вершины
        int[] inDegree = new int[n];

        // Построение графа и подсчет степеней захода
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (adjacencyMatrix[u][v] == 1) {
                    graph.get(u).add(v);
                    inDegree[v]++;
                }
            }
        }

        // Очередь для вершин с нулевой степенью захода
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);

            for (int v : graph.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        // Если не все вершины были добавлены, граф содержит цикл
        if (result.size() != n) {
            throw new IllegalArgumentException("Граф содержит цикл, топологическая сортировка невозможна.");
        }

        return result;
    }
}