

/*
 *  PunctuationsRemover 
 *      removes excessive whitespace sequences in the Arabic text,then
 *      removes all punctuation marks that usually appear in MSA 
 *      
 *      
 *  For bug reports, and fixes contact:
 *    Maha Althobaiti
 *    mjaltha@essex.ac.uk
 *  
 */

package utilities;

import java.util.ArrayList;

public class PunctuationsRemover {
	
	 public String removePunctuations(String currentText)
     {
		
    ArrayList<String> tokens=new ArrayList<String>();
  	StringBuffer modifiedWord=new StringBuffer("");
  	
	     ArrayList<String> modifiedText=removeExtraSpaces(currentText);
	     for (int i=0;i<modifiedText.size();i++)
	     {
	    	 modifiedWord.setLength(0);
	    	 String ctoken=modifiedText.get(i);
	    	 for (int j=0; j<ctoken.length(); j++)
	    	{
	    		 if ( ! (Constants.punctuations.contains(ctoken.substring(j,j+1))))
	                 modifiedWord.append(ctoken.substring(j,j+1));
	             else
	             {
	                 
	             }
	    	}
	    	 tokens.add(modifiedWord.toString());
	     }  // for each token in the text
	String result="";
	for (String t:tokens)
	{
		result=result+t+" ";
		
	}
	result=result.substring(0,result.length()-1);
	return result;
     }

	private ArrayList<String> removeExtraSpaces(String currentText) {
		ArrayList<String> tt=new ArrayList<String>();
		  StringBuffer word = new StringBuffer ( );
		  currentText=currentText+" ";
		  for ( int i=0;i<currentText.length();i++)
            {
          // if the character is not a space, add it to a word
          if(( ! Character.isWhitespace(currentText.charAt(i))))
               {
              word.append(currentText.charAt(i));
               }
          else
             {
              if (word.length() != 0)
                {
                  tt.add(word.toString());
                  word.setLength ( 0 );
                }
              }
            }
		return tt;
	    }
}

