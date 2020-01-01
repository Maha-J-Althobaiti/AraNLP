/*
 *  TrainedTokenizer is used to segment Arabic text into tokens. It
 *  is built based on Maximum entropy model, trained on documents from 
 *  different genres collected from Arabic Wikipedia collection.
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

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TrainedTokenizer {
public TrainedTokenizer(){}
	

    public String[] tokenize(String line) throws FileNotFoundException, IOException {

        String tokens[] = null;

        InputStream modelIn = new FileInputStream("arabic-token.bin");

        try {
            TokenizerModel model = new TokenizerModel(modelIn);
            TokenizerME tokenizer = new TokenizerME(model);
            tokens = tokenizer.tokenize(line);
            } 
        catch (IOException e) 
           {
            e.printStackTrace();
           } 
        finally 
        {
            if (modelIn != null) {
                try {
                    modelIn.close();
                    } 
                catch (IOException e) {
                   System.out.println(e.getMessage());
                }
            }
        }

        return tokens;
       }
}
