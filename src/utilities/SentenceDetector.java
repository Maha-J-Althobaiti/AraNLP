
/*
 *  SentenceDetector is used to recognise sentence boundaries of
 *  the Arabic Text. It is built based on Maximum entropy model,
 *  trained on sentences extracted from Arabic Wikipedia collection
 *  using OpenNLP machine learning toolkit.
 *  
 *  OpenNLP software, developed at The Apache Software Foundation 
 *  (http://http://www.apache.org/), was used in order to train and use the model. 
 *        
 *  For bug reports, and fixes contact:
 *    Maha Althobaiti
 *    mjaltha@essex.ac.uk
 *  
 */

package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetector {

	public SentenceDetector(){}

	public String[] detectSentences(String text) throws FileNotFoundException
	     {
		String sentences[] = null;

	    InputStream modelIn = new FileInputStream("arabic-sen.bin");

	    try {
	        SentenceModel model = new SentenceModel(modelIn);
	        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
	        sentences = sentenceDetector.sentDetect(text);
	        } 
	    catch (IOException e) 
	        {
	        e.printStackTrace();
	         } 
	     finally 
	     {
	        if (modelIn != null)
	        {
	        	try 
	        	{
	                modelIn.close();
	            } catch (IOException e) {
	            	System.out.println(e.getMessage());
	            }
	        }
	    }
	     
	 return sentences;    
	 }

}
