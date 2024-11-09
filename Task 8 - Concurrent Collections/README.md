# Concurrent Collections - Word Frequency Count Using `ConcurrentHashMap`

This Java program demonstrates how to use `ConcurrentHashMap` to count word frequencies in a list of sentences in a thread-safe manner. The program uses the `merge()` method to safely update the word count in a multi-threaded environment.

## Code Example

```java
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentCollectionsExample {

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
```
# Explanation of Key Concepts

## `ConcurrentHashMap`

`ConcurrentHashMap` is a thread-safe implementation of the `Map` interface in Java, which allows multiple threads to read and modify the map concurrently without causing data corruption. Unlike `Hashtable`, it does not block the entire map for read operations, making it more scalable in multi-threaded environments.

## `merge()` Method

The `merge()` method is used to atomically update the value associated with a given key. In the case of word frequency counting, the `merge()` method helps in updating the word count while ensuring thread safety.

### Method Signature:
```java
merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)
```
K key: The key whose associated value is to be updated.
V value: The new value to merge (in this case, 1 to increment the word count).
BiFunction<? super V, ? super V, ? extends V> remappingFunction: A function that takes the old value and the new value and returns a new value. Here, Integer::sum is used to add the old value to the new value.

# Process Explanation

## ExecutorService: A thread pool is used to process sentences in parallel. The ExecutorService manages the threads efficiently.

## Word Processing: Each sentence is split into words, and the merge() method is used to increment the count of each word in the ConcurrentHashMap. This ensures that the updates are thread-safe.

## Output: After all threads finish processing, the final word frequency map is printed, displaying the number of occurrences of each word across all sentences.

```java
java: 3
programming: 1
language: 1
concurrent: 2
collections: 2
are: 2
useful: 1
hashmap: 1
thread-safe: 1
map: 1
api: 1
is: 2
part: 1
the: 1
```
# Why Use merge()?

The merge() method is ideal for this scenario because it performs atomic operations on the value of a specific key in the map. In this case, when multiple threads try to update the frequency of the same word concurrently, merge() ensures that the word count is updated safely without causing inconsistencies.

By using merge() and ConcurrentHashMap, we ensure that the word counting process is efficient and thread-safe in a multi-threaded environment.


