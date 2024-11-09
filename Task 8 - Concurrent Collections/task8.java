// 8. Concurrent Collections
// Problem: Create a program that uses a ConcurrentHashMap to count word frequencies in a list of sentences.
// Task: Write a Java program that takes a list of sentences, splits them into words, and counts the frequency of each word using a ConcurrentHashMap.

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class task8 {

    public static void main(String[] args) {
        // Sample list of sentences
        List<String> sentences = List.of(
                "Java is a programming language",
                "Concurrent collections are useful",
                "Java collections are part of the Java API",
                "ConcurrentHashMap is a thread-safe map"
        );

        // Create a ConcurrentHashMap to store word frequencies
        ConcurrentHashMap<String, Integer> wordFrequencyMap = new ConcurrentHashMap<>();

        // Create an ExecutorService with a fixed thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Process each sentence in parallel using ExecutorService
        for (String sentence : sentences) {
            executorService.submit(() -> processSentence(sentence, wordFrequencyMap));
        }

        // Shutdown the executor service
        executorService.shutdown();

        // Wait for all tasks to finish
        while (!executorService.isTerminated()) {
        }

        // Print out the word frequencies
        wordFrequencyMap.forEach((word, count) -> 
            System.out.println(word + ": " + count));
    }

    /**
     * Processes a sentence and counts the frequency of each word.
     * 
     * @param sentence The sentence to process
     * @param wordFrequencyMap The map to store the word frequencies
     */
    private static void processSentence(String sentence, ConcurrentHashMap<String, Integer> wordFrequencyMap) {
        // Split the sentence into words
        String[] words = sentence.split("\\s+");

        // Iterate through each word and update the frequency in the map
        for (String word : words) {
            wordFrequencyMap.merge(word.toLowerCase(), 1, Integer::sum);
        }
    }
}
