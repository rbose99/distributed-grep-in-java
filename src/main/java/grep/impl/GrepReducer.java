/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package grep.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import grep.util.Pair;

public class GrepReducer {

    public void reduce(List<Pair<String, String>> mappedData, String outputPath) {
        System.out.println("Number of matched lines: " + mappedData.size());
        try (var writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (Pair<String, String> entry : mappedData) {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
