package ru.t1.education;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {

    @Test
    void testSimpleDAG() {
        int[][] matrix = {
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertEquals(expected, result);
    }

    @Test
    void testMultiplePaths() {
        int[][] matrix = {
            {0, 1, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        boolean isValid = result.equals(Arrays.asList(0, 1, 2, 3)) || result.equals(Arrays.asList(0, 2, 1, 3));
        assertTrue(isValid, "Ошибка в testMultiplePaths: " + result);
    }

    @Test
    void testCycle() {
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        try {
            TopologicalSort.topologicalSort(matrix);
            assert false : "Ошибка в testCycle: ожидалось исключение";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("цикл") : "Ошибка в testCycle: некорректное исключение";
        }
    }

    @Test
    void testIsolatedNodes() {
        int[][] matrix = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        List<Integer> expected = Arrays.asList(0, 1, 2);
        assert new HashSet<>(result).containsAll(expected) && expected.containsAll(result) : "Ошибка в testIsolatedNodes: " + result;
    }

    public void topologicalSort() {
        testSimpleDAG();
        testMultiplePaths();
        testCycle();
        testIsolatedNodes();
        System.out.println("Все тесты пройдены!");
    }
}