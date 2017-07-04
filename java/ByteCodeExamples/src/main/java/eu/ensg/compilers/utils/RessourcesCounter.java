/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ensg.compilers.utils;

/**
 *
 * @author cyann
 */
public class RessourcesCounter {

    private int value;
    private int max;

    public RessourcesCounter(int value) {
        this.value = value;
        this.max = value;
    }

    public RessourcesCounter increment() {
        value++;
        if (max < value) {
            max = value;
        }
        return this;
    }

    public RessourcesCounter decrement() {
        value--;
        return this;
    }

    public int getValue() {
        return value;
    }

    public int getMax() {
        return max;
    }

}
