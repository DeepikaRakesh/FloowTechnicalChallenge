# FloowTechnicalChallenge

So, I have implemented this challenge in 2 ways.
1. By writing a simple java program
2. By using MongoDB MapReduce 

Please find a brief explanation on each of these approaches.

1. WordOccuranceInFile.java

The simple idea here is, I built a word Map by reading contents of a text File. This Map should contain word as a key and their count as value. Once I have this Map ready, I can simply sort the Map based upon values.
But the challenge here is, HashMap does not maintain order. Thats the reason I have used list here to keep the entry in sorted order.
Once I get the list, I can simply loop over the list and print each key and value from the list. Here are the steps.

Step 1 : Created a HashMap object called wordCountMap which will hold words of the input file as keys and their occurrences as values.
HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();

Step 2 : Created BufferedReader object to read the input text file.
BufferedReader reader = new BufferedReader(new FileReader(“Pass The File Location Here”));

Step 3 : Read all the lines of input text file one by one into currentLine using reader.readLine() method.
String currentLine = reader.readLine();

Step 4 : Split the currentLine into words by using space as delimiter. Use toLowerCase() method here if we don’t want case sensitiveness.
String[] words = currentLine.toLowerCase().split(” “);

Step 5 : Iterate through each word of words array and check whether the word is present in wordCountMap. If word is already present in wordCountMap, update its count. Otherwise insert the word as a key and 1 as its value.
if(wordCountMap.containsKey(word))
{ 
         wordCountMap.put(word, wordCountMap.get(word)+1);
}
else
{
         wordCountMap.put(word, 1);
}

Step 6 : Get the mostRepeatedWord and its count by iterating through each entry of the wordCountMap.

Step 7 : Close the resources.

Since Map.Entry doesn't implement the Comparable interface, I wrote a custom Comparator to sort the entries and I am comparing entries on their values.

With a large file, program will run out of memory and throw java.lang.OutOfMemory: Java Heap space. One solution for this is to do this task in chunk e.g. first read 20% content, find maximum repeated word on that, then read next 20% content and find repeated maximum by taking the previous maximum in consideration. 
In this way, we don't need to store all words in the memory and can process any arbitrary length file.


2. MapReduce - MongoDB

Firstly, Let me tell you that I dont have any experience in MongoDB, Its just that I took this opportunity to learn something new and tried to do a bit of research.
Here is the information I extracted out of my research.

MapReduce is a programming model and an associated implementation for processing and generating big data sets. For map-reduce operations, MongoDB provides the mapReduce database command.
MapReduce runs on a large cluster of commodity machines and is highly scalable. It has several forms of implementation provided by multiple programming languages, like Java, C# and C++.

The MapReduce framework has two parts:

1. A function called "Map," which allows different points of the distributed cluster to distribute their work
2. A function called "Reduce," which is designed to reduce the final form of the clusters’ results into one output

Advantage :

The main advantage of the MapReduce framework is its fault tolerance, where periodic reports from each node in the cluster are expected when work is completed.

So I have tried applying Map-Reduce on the “TextFileCollection” collection to get a count of each individual word contained in the entire text file. 
In MongoDB Map-Reduce, we can write our map and reduce functions using javascript (WordCount_Map.js and WordCount_Reduce.js). "this.content" will return the content of the page.
In Map-Reduce, the reduce function will get all values for a particular key as an array. So in the reduce function, we have to get the total of counts to calculate the total number of occurrences of a particular word.

And then, I wrote a simple Java code to execute Map-Reduce on “TextFileCollection” collection. In the above Java code, first connected to MongoDB database ("sampleMongoDB") and get a reference to the “TextFileCollection” collection. 
Then read map and reduce functions as a String from the classpath. Finally execute the “mapReduce()” method on our input collection. This will apply our map and reduce functions on the “TextFileCollection” collection and store the output in a new collection called “wordcounts”. 
If there is an already existing “wordcounts” collection, it will be replaced by the new one.
