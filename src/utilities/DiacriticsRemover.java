

/*
 *  DiacriticRemover 
 *      removes excessive whitespace sequences in the Arabic text,then
 *      removes all diacritic marks that usually appear in MSA
 *      1- TANWEEN_ALFATHA, 2- TANWEEN_ALDAMMA, 3- TANWEEN_ALKASRA, 
 *      4- FATHA, 5- DAMMA, 6- KASRA,
 *      7- SHADDA 8- SUKUN. 
 *      
 *      
 *  For bug reports, and fixes contact:
 *    Maha Althobaiti
 *    mjaltha@essex.ac.uk
 *  
 */
package utilities;

import java.util.ArrayList;

public class DiacriticsRemover {
	
    // remove all diacritics from text	
    public String removeDiacritics(String currentText)
       {
		ArrayList<String> tokens=new ArrayList<String>();
    	StringBuffer modifiedWord=new StringBuffer("");
	    ArrayList<String> modifiedText=removeExtraSpaces(currentText);
	    
	    // for each token in the text
	    for (int i=0;i<modifiedText.size();i++)
	     {
	    	 modifiedWord.setLength(0);
	    	 String ctoken=modifiedText.get(i);
	    	 for (int j=0; j<ctoken.length(); j++)
	    	{
	    		 if ( ! (Constants.diacritics.contains(ctoken.substring(j,j+1))))
	                 modifiedWord.append(ctoken.substring(j,j+1));
	             else
	             {
	                 
	             }
	    	}
	    	 tokens.add(modifiedWord.toString());
	     }  
	        String result="";
	
	   for (String t:tokens)
	      {
		 result=result+t+" ";
	      }
	
	result=result.substring(0,result.length()-1);
	return result;
        }

	private ArrayList<String> removeExtraSpaces(String currentText) 
	{
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
