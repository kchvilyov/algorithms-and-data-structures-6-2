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

    // Тесты
    public static void main(String[] args) {
        testSimpleDAG();
        testMultiplePaths();
        testCycle();
        testIsolatedNodes();
        System.out.println("Все тесты пройдены!");
    }

    private static void testSimpleDAG() {
        int[][] matrix = {
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        List<Integer> result = topologicalSort(matrix);
        assert result.equals(expected) : "Ошибка в testSimpleDAG: " + result;
    }

    private static void testMultiplePaths() {
        int[][] matrix = {
            {0, 1, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        List<Integer> result = topologicalSort(matrix);
        boolean isValid = result.equals(Arrays.asList(0, 1, 2, 3)) || result.equals(Arrays.asList(0, 2, 1, 3));
        assert isValid : "Ошибка в testMultiplePaths: " + result;
    }

    private static void testCycle() {
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        try {
            topologicalSort(matrix);
            assert false : "Ошибка в testCycle: ожидалось исключение";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("цикл") : "Ошибка в testCycle: некорректное исключение";
        }
    }

    private static void testIsolatedNodes() {
        int[][] matrix = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        List<Integer> result = topologicalSort(matrix);
        List<Integer> expected = Arrays.asList(0, 1, 2);
        assert new HashSet<>(result).containsAll(expected) && expected.containsAll(result) : "Ошибка в testIsolatedNodes: " + result;
    }
}