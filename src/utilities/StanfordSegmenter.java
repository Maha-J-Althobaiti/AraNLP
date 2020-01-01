/*
 * Stanford Word Segmenter produces Penn Arabic Treebank (ATB) three clitic segmentation: 
 *  conjunctions, prepositions, and pronouns. 
 *        
 *  
 */

package utilities;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.international.arabic.process.ArabicSegmenter;
import edu.stanford.nlp.ling.HasWord;

public class StanfordSegmenter {
	
	public String segment(String text)
	{
		Properties props = new Properties();
	    ArabicSegmenter seg = new ArabicSegmenter(props);
	    seg.loadSegmenter("data/arabic-segmenter-atbtrain.ser.gz");
	    
	    String[] tokens=text.split("\\s");
	    StringBuffer segTokens=new StringBuffer();
	    for (int i=0;i<tokens.length;i++)
	    {
	    List<HasWord> txtseg=seg.segment(tokens[i]);
	    
	    String txtAfterseg="";
	    for(HasWord s:txtseg)
	         {
	    	// System.out.println(s.word()); 
	    	 txtAfterseg+=s.word()+" ";	 
	         }
	    segTokens.append(txtAfterseg+" ");
	    }
		return segTokens.toString().trim();
	}

}
