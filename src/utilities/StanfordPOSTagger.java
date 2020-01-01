
package utilities;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.international.arabic.process.ArabicSegmenter;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordPOSTagger {
	
	public String tagText(String text) throws IOException, ClassNotFoundException
	{
		MaxentTagger tagger = new MaxentTagger("data/arabic-accurate.tagger");
		
		Properties props = new Properties();
	    ArabicSegmenter seg = new ArabicSegmenter(props);
	    seg.loadSegmenter("data/arabic-segmenter-atbtrain.ser.gz");
	    
	    String[] tokens=text.split("\\s");
	    StringBuffer segTokens=new StringBuffer();
	    
	    for (int i=0;i<tokens.length;i++)
	    {
	    List<HasWord> txtseg=seg.segment(tokens[i]);
	    System.out.println(txtseg); // return List<HasWord>
	    String txtAfterseg="";
	    for(HasWord s:txtseg)
	         {
	    	
	    	 txtAfterseg+=s.word()+" ";	 
	         }
	    segTokens.append(txtAfterseg+" ");
	    } 
		
		
		
		// The tagged string
		String tagged = tagger.tagString(segTokens.toString().trim());
		
		return tagged;
	}

}
