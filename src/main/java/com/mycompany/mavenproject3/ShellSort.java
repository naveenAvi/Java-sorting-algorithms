/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author navee
 */
public class ShellSort {
    public double[] SortTheArray(double arr[]) {
        int n = arr.length;
 
        // Start with an initial gap and reduce it by half in each iteration
        for (int gap = n / 2; gap > 0; gap /= 2) {
 
            // Perform gapped insertion sort for the current gap size
            for (int i = gap; i < n; i += 1) {
                double temp = arr[i]; // Temporarily store the current element
 
                int j;
                // Shift elements to the right to make space for the current element
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp; // Place the current element in its correct position
            }
        }
        return arr; // Return the sorted array
    }
 }
 
