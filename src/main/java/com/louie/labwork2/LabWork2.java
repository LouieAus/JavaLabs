package com.louie.labwork2;

import java.util.HashSet;
import java.util.Set;

import java.util.HashMap;
import java.util.Map;

public class LabWork2
{
    public static void main(String[] args)
    {
        
    }
}

class Funcs
{
    // Задание 1: Найти наибольшую строку без повторяющихся символов
    static String getBiggestStr(String s)
    {
        if (s.isEmpty()) {
            return "";
        }

        Set<Character> seen = new HashSet<>();
        int start = 0;
        int maxLength = 0;
        int startIndex = 0;

        for (int end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);

            while (seen.contains(currentChar)) {
                seen.remove(s.charAt(start));
                start++;
            }

            seen.add(currentChar);

            if ((end - start + 1) > maxLength) {
                maxLength = end - start + 1;
                startIndex = start;
            }
        }

        return s.substring(startIndex, startIndex + maxLength);
    }
    
    // Задание 2: Объединить два отсортированных массива
    static int[] mergeSortedArrays(int[] arr1, int[] arr2)
    {
        int[] mergedArray = new int[arr1.length + arr2.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                mergedArray[k] = arr1[i];
                i++;
            } else {
                mergedArray[k] = arr2[j];
                j++;
            }
            k++;
        }

        while (i < arr1.length) {
            mergedArray[k] = arr1[i];
            i++;
            k++;
        }

        while (j < arr2.length) {
            mergedArray[k] = arr2[j];
            j++;
            k++;
        }

        return mergedArray;
    }
    
    // Задание 3: Найти максимальную сумму подмассива
    static int maxSubArraySum(int[] arr)
    {
        if (arr.length == 0) {
            return 0;
        }

        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }
    
    // Задание 4: Повернуть массив на 90 градусов по часовой стрелке
    static int[][] rotateClockwise(int[][] matrix)
    {
        if (matrix.length == 0) {
            return new int[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] rotatedMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - 1 - i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }
    
    // Задание 5: Найти пару элементов в массиве, сумма которых равна заданному числу
    static int[] findPair(int[] arr, int target)
    {
        Map<Integer, Integer> seenNumbers = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];
            if (seenNumbers.containsKey(complement)) {
                return new int[]{arr[i], complement};
            }
            seenNumbers.put(arr[i], i);
        }

        return null;
    }
    
    // Задание 6: Найти сумму всех элементов в двумерном массиве
    static int sumArray(int[][] arr)
    {
        if (arr.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int[] row : arr) {
            for (int element : row) {
                sum += element;
            }
        }
        return sum;
    }
    
    // Задание 7: Найти максимальный элемент в каждой строке двумерного массива
    static int[] maxElementsRow(int[][] arr)
    {
        if (arr.length == 0) {
            return new int[0];
        }

        int[] maxElements = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null || arr[i].length == 0) {
                maxElements[i] = 0;
            }
            else {
                int max = arr[i][0];
                for (int j = 1; j < arr[i].length; j++) {
                    if (arr[i][j] > max) {
                        max = arr[i][j];
                    }
                }
                maxElements[i] = max;
            }
        }

        return maxElements;
    }
    
    // Задание 8: Повернуть двумерный массив на 90 градусов против часовой стрелке
    static int[][] rotateAntiClockwise(int[][] matrix)
    {
        if (matrix.length == 0) {
            return new int[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] rotatedMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[cols - 1 - j][i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }
}