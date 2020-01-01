/*
 *  SpaceTokenizer is used to segment Arabic text into tokens. It
 *  removes excessive whitespace sequences and considers 
 *  non whitespace sequences as tokens  
 *        
 *  For bug reports, and fixes contact:
 *    Maha Althobaiti
 *    mjaltha@essex.ac.uk
 *  
 */

package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SpaceTokenizer {
	
	public ArrayList<String> tokenize(String text) throws FileNotFoundException, IOException {

      ArrayList<String> tokens=new ArrayList<String>();
	  StringBuffer word = new StringBuffer ( );
	  text=text+" ";
	  for ( int i=0;i<text.length();i++)
        {
		  
      // if the character is not a space, add it to a word
      if(( ! Character.isWhitespace(text.charAt(i))))
           {
          word.append(text.charAt(i));
           }
      else
         {
          if (word.length() != 0)
            {
              tokens.add(word.toString());
              word.setLength ( 0 );
            }
          }
        }
	return tokens;
	}

}
