package wordOccurance;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.io.InputStream;

public class WordCount_MapReduceExample {

	public static void main(String[] args) {
		try {
			// create a MongoClient by connecting to the MongoDB instance in localhost

			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// drop database if it already exists

			mongoClient.dropDatabase("sampleMongoDB");

			// access the db named "samplesampleMongoDB"

			DB db = mongoClient.getDB("sampleMongoDB");
			
			// access the input collection

			DBCollection collection = db.getCollection("TextFileCollection");
			
			// read Map file

			String map = readFile("WordCount_Map.js");

			// read Reduce file

			String reduce = readFile("WordCount_Reduce.js");

			// execute MapReduce on the input collection and direct the result to
			// "wordcounts" collection

			collection.mapReduce(map, reduce, "wordcounts", MapReduceCommand.OutputType.REPLACE, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the specified file from classpath
	 */
	private static String readFile(String fileName) throws IOException {
		
		// get the input stream

		InputStream fileStream = WordCount_MapReduceExample.class.getResourceAsStream("/" + fileName);

		// create a buffer with some default size

		byte[] buffer = new byte[8192];

		// read the stream into the buffer

		int size = fileStream.read(buffer);

		// create a string for the needed size and return

		return new String(buffer, 0, size);
	}
}