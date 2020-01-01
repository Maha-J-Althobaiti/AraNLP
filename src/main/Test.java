package main;

import java.io.IOException;

import utilities.AraNormalizer;
import utilities.DiacriticsRemover;
import utilities.PunctuationsRemover;
import utilities.RootStemmer;
import utilities.SentenceDetector;
import utilities.TrainedTokenizer;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Example of using the modules within a simple pipeline
		String arabicText="ذَهَبْــــــــــــتٌ هند إلى الجامعٍة..";
		SentenceDetector sd=new SentenceDetector();
		TrainedTokenizer tok=new TrainedTokenizer();
		RootStemmer rs=new RootStemmer();
		AraNormalizer arn=new AraNormalizer();
		DiacriticsRemover dr=new DiacriticsRemover();
		PunctuationsRemover pr=new PunctuationsRemover();
		
		String normalizedText=arn.normalize(arabicText);
		normalizedText = dr.removeDiacritics(normalizedText);
		normalizedText = pr.removePunctuations(normalizedText);
		String[] tokens=tok.tokenize(normalizedText);
		String[] sentences=sd.detectSentences(normalizedText);
		
		for (int i=0;i<sentences.length;i++)
			System.out.println(sentences[i]);
		System.out.println("******************************");
		
		String newText="";
		for (int i=0;i<tokens.length;i++)
		{
		 String stem=rs.findRoot(tokens[i]);
		 newText=newText+stem+" ";
		}

		System.out.println("Text after stemming: "+newText);


	}

}
