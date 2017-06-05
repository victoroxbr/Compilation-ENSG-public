/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamples;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author cyann
 */
public class Functors {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jean", "Paul", "Claude", "Charles");

        Function<String, String> formater = new Function<String, String>() {

            @Override
            public String apply(String t) {
                return "The name is " + t + ".";
            }

        };

        names.stream().map(formater).forEach(new Consumer<String>() {
            @Override
            public void accept(String t) {
                System.out.println(t);
            }
            
        });
        
    }

}
