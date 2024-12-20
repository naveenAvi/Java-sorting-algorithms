/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 *
 */
public class MergeSort {
    public double[] SortTheArray(double[] arr) {
        if (arr.length <= 1) {
            return arr; // Base case: arrays of size 1 or less are already sorted
        }
        
        // Split the array into two halves
        int mid = arr.length / 2;
        double[] left = new double[mid];
        double[] right = new double[arr.length - mid];
        
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);
        
        // Recursively sort the two halves
        left = SortTheArray(left);
        right = SortTheArray(right);
        
        // Merge the sorted halves
        return merge(left, right);
    }
    
    private double[] merge(double[] left, double[] right) {
        int leftIndex = 0, rightIndex = 0, mergedIndex = 0;
        double[] merged = new double[left.length + right.length];
        
        // Merge elements from both arrays in sorted order
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                merged[mergedIndex++] = left[leftIndex++];
            } else {
                merged[mergedIndex++] = right[rightIndex++];
            }
        }
        
        // Copy remaining elements from the left array (if any)
        while (leftIndex < left.length) {
            merged[mergedIndex++] = left[leftIndex++];
        }
        
        // Copy remaining elements from the right array (if any)
        while (rightIndex < right.length) {
            merged[mergedIndex++] = right[rightIndex++];
        }
        return merged; // Return the merged and sorted array
    }
}

