/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * 
 */
public class QuickSort {
    public double[] SortTheArray(double[] arr) {
        quickSort(arr, 0, arr.length - 1); // Start the quick sort with the full array
        return arr;
    }

    private void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high); // Find the partition index

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(double[] arr, int low, int high) {
        double pivot = arr[high]; // Choose the last element as the pivot
        int i = (low - 1);

        // Rearrange elements based on the pivot
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // Swap elements to place smaller values before the pivot
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place the pivot in its correct position
        double temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // Return the partition index
    }
}

