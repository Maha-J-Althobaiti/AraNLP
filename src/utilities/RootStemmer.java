/*
 *  RootStemmer is root extraction tool based on 'ArabicStemmer' algorithm, developed by shereen Khoja,
 *  The 'ArabicStemmer' has been modified, so that it can be used easily within our AraNLP library. 
 *   We also exempted 'stop words' from stemming instead of removing them, as in the 'ArabicStemmer'
 *  
 *    
 *  ArabicStemmer can be found in 
 *  http://zeus.cs.pacificu.edu/shereen/research.htm
 *  
 *  To use RootStemmer, recall findRoot function 
 *    parameter: Arabic token (NOT Text)
 *    return: root of the Arabic token 
 *  
 */

package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class RootStemmer {
	  
    private static boolean rootFound = false;
    private static boolean stopwordFound = false;
    private static boolean fromSuffixes = false;
    private static boolean rootNotFound = false;
    
    private static String roots="";

    private static Vector<Vector<String>> staticFiles ;
    static String  pathToStemmerFiles = new StringBuffer ( System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" ) + "StemmerFiles" + System.getProperty ( "file.separator" ) ).toString ( );
    


	public static String getRoots() {
		return roots;
	}

	public static void setRoots(String roots) {
		RootStemmer.roots = roots;
	}
	
	 /** Creates new form ASASinterface */
    public RootStemmer() {
        initVariables();
    }

    private static void initVariables()
    {
        rootFound = false;
        stopwordFound = false;
        fromSuffixes = false;
        rootNotFound = false;
        staticFiles = new Vector<Vector<String>> ( );
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "definite_article.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "duplicate.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "first_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "first_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_alif.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_hamza.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_maksoura.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "last_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "mid_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "mid_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "prefixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "punctuation.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "quad_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "stopwords.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "suffixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "tri_patt.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "tri_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "diacritics.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "strange.txt" ).toString ( ) ) )
            {
            //System.out.println( "Dictionaries have been read successfully" );
            }
    }
 
    // find the root of the given token 
    public String findRoot ( String token )
    {   
        initVariables();
        token=token.trim();
		String[] txtToken=token.split("\\s");
		if (txtToken.length > 1)
		{
            return "error: the invoked function can find a root for one token at a time!";
		}
		else
		{
		boolean stop=checkStopwords(token);
		if (stop)
			return token;
		else
		{
        // if the word contains two letters
        if ( token.length ( ) == 2 )
        	token = isTwoLetters ( token );

        // if the word contains three letters
        if( token.length ( ) == 3 && !rootFound )
        	token = isThreeLetters ( token );

        // if the word contains four letters
        if( token.length ( ) == 4 )
            isFourLetters ( token );

        if( !rootFound )
        {
            // check the pattern
        	token = checkPatterns ( token );
        }

        if ( !rootFound )
        {
            // look for Definite Articles and remove them
        	token = checkDefiniteArticle ( token );
        }

        if ( !rootFound && !stopwordFound )
        {
            // look for Prefix waaw and remove it
        	token = checkPrefixWaw ( token );
        }

        if ( !rootFound && !stopwordFound )
        {
            // look for suffixes and remove them
        	token = checkForSuffixes ( token );
        }

        if ( !rootFound && !stopwordFound )
         {
        	// look for prefixes and remove them
        	token = checkForPrefixes ( token );
         }
        return token;
		 }
	   }  
    }
    
        // look for prefixes and remove them
    private static String checkForPrefixes ( String word )
    {   
        String prefix = "";
        String modifiedWord = word;
        @SuppressWarnings("rawtypes")
		Vector prefixes = ( Vector ) staticFiles.elementAt ( 10 );
        
        for ( int i = 0; i < prefixes.size ( ); i++ )
        {
            prefix = ( String ) prefixes.elementAt ( i );

            if ( prefix.regionMatches ( 0, modifiedWord, 0, prefix.length ( ) ) )
            {
                modifiedWord = modifiedWord.substring ( prefix.length ( ) );

                if ( checkStopwords( modifiedWord ) )
                    return modifiedWord;

                if ( modifiedWord.length ( ) == 2 )
                    modifiedWord = isTwoLetters ( modifiedWord );
                else if ( modifiedWord.length ( ) == 3 && !rootFound )
                    modifiedWord = isThreeLetters ( modifiedWord );
                else if ( modifiedWord.length ( ) == 4 )
                    isFourLetters ( modifiedWord );

                if ( !rootFound && modifiedWord.length ( ) > 2 )
                    modifiedWord = checkPatterns ( modifiedWord );

                if ( !rootFound && !stopwordFound && !fromSuffixes)
                {
                    modifiedWord = checkForSuffixes ( modifiedWord );
                }

                if ( stopwordFound )
                    return modifiedWord;

                if ( rootFound && !stopwordFound )
                {
                    return modifiedWord;
                }
            }
        }
        return word;
    }
    
    
    private static boolean checkStopwords ( String currentWord )
    {   
        @SuppressWarnings("rawtypes")
		Vector v = ( Vector ) staticFiles.elementAt ( 13 );
        stopwordFound = v.contains ( currentWord );
        return stopwordFound;
    }
    
     // words with two letters
    private static String isTwoLetters ( String word )
    {
        // look for deleted or duplicated letter
        word = duplicate ( word );

        // if the last letter is weak
        if ( !rootFound )
            word = lastWeak ( word );

        // if the first letter is weak
        if ( !rootFound )
            word = firstWeak ( word );

        // if the middle letter is weak
        if ( !rootFound )
            word = middleWeak ( word );

    return word;
    }
    
     // words with three letters
    @SuppressWarnings("rawtypes")
	private static String isThreeLetters ( String word )
    {   
        
        StringBuffer modifiedWord = new StringBuffer ( word );
        String root = "";
        // change hamzated alif to bare alif
        if ( word.length ( ) > 0 )
        {
            if ( word.charAt ( 0 ) == '\u0627' || word.charAt ( 0 ) == '\u0624' || word.charAt ( 0 ) == '\u0626' )
            {
                modifiedWord.setLength ( 0 );
                modifiedWord.append ( '\u0623' );
                modifiedWord.append ( word.substring ( 1 ) );
                root = modifiedWord.toString ( );
            }

            if ( word.charAt ( 2 ) == '\u0648' || word.charAt ( 2 ) == '\u064a' || word.charAt ( 2 ) == '\u0627' ||
                 word.charAt ( 2 ) == '\u0649' || word.charAt ( 2 ) == '\u0621' || word.charAt ( 2 ) == '\u0626' )
            {
                root = word.substring ( 0, 2 );
                root = lastWeak ( root );
                if ( rootFound )
                {
                    return root;
                }
            }

            if ( word.charAt ( 1 ) == '\u0648' || word.charAt ( 1 ) == '\u064a' || word.charAt ( 1 ) == '\u0627' || word.charAt ( 1 ) == '\u0626' )
            {
                root = word.substring ( 0, 1 );
                root = root + word.substring ( 2 );

                root = middleWeak ( root );
                if ( rootFound )
                {
                    return root;
                }
            }

            if ( word.charAt ( 1 ) == '\u0624' || word.charAt ( 1 ) == '\u0626' )
            {
                if ( word.charAt ( 2 ) == '\u0645' || word.charAt ( 2 ) == '\u0632' || word.charAt ( 2 ) == '\u0631' )
                {
                    root = word.substring ( 0, 1 );
                    root = root + '\u0627';
                    root = root+ word.substring ( 2 );
                }
                else
                {
                    root = word.substring ( 0, 1 );
                    root = root + '\u0623';
                    root = root + word.substring ( 2 );
                }
            }

            if ( word.charAt ( 2 ) == '\u0651')
            {
                root = word.substring ( 0, 1 );
                root = root + word.substring ( 1, 2 );
            }
        }

        // if word is a root, then rootFound is true
        if ( root.length ( ) == 0 )
        {
            if ( ( ( Vector ) staticFiles.elementAt ( 16 ) ) .contains ( word ) )
            {
                rootFound = true;
                if ( rootNotFound )
                {
                    rootNotFound = false;
                }
                return word;
            }
        }
        else if ( ( ( Vector ) staticFiles.elementAt ( 16 ) ) .contains ( root ) )
        {
            rootFound = true;
            if ( rootNotFound )
            {
                rootNotFound = false;
            }
            return root;
        }
        return word;
    }
    
    // words with four letters
    @SuppressWarnings("rawtypes")
	private static void isFourLetters ( String word )
    {   
        if( ( ( Vector ) staticFiles.elementAt ( 12 ) ) .contains ( word ) )
        {
            rootFound = true;
        }
    }
    
    // match the words to the patterns
    @SuppressWarnings("rawtypes")
	private static String checkPatterns ( String word )
    {   
        StringBuffer root = new StringBuffer ( "" );
        if ( word.length ( ) > 0 )
            if ( word.charAt ( 0 ) == '\u0623' || word.charAt ( 0 ) == '\u0625' || word.charAt ( 0 ) == '\u0622' )
            {
                root.append ( "j" );
                root.setCharAt ( 0, '\u0627' );
                root.append ( word.substring ( 1 ) );
                word = root.toString ( );
            }

        Vector patterns = ( Vector ) staticFiles.elementAt ( 15 );
        int numberSameLetters = 0;
        String pattern = "";
        String modifiedWord = "";

        // for every pattern
        for( int i = 0; i < patterns.size ( ); i++ )
        {
            pattern = ( String ) patterns.elementAt ( i );
            root.setLength ( 0 );
            if ( pattern.length ( ) == word.length ( ) )
            {
                numberSameLetters = 0;
                for ( int j = 0; j < word.length ( ); j++ )
                    if ( pattern.charAt ( j ) == word.charAt ( j ) &&
                         pattern.charAt ( j ) != '\u0641'          &&
                         pattern.charAt ( j ) != '\u0639'          &&
                         pattern.charAt ( j ) != '\u0644'            )
                        numberSameLetters ++;

                if ( word.length ( ) == 6 && word.charAt ( 3 ) == word.charAt ( 5 ) && numberSameLetters == 2 )
                {
                    root.append ( word.charAt ( 1 ) );
                    root.append ( word.charAt ( 2 ) );
                    root.append ( word.charAt ( 3 ) );
                    modifiedWord = root.toString ( );
                    modifiedWord = isThreeLetters ( modifiedWord );
                    if ( rootFound )
                        return modifiedWord;
                    else
                        root.setLength ( 0 );
                }

                // if the word matches the pattern, get the root
                if ( word.length ( ) - 3 <= numberSameLetters )
                {
                    for ( int j = 0; j < word.length ( ); j++ )
                        if ( pattern.charAt ( j ) == '\u0641' ||
                             pattern.charAt ( j ) == '\u0639' ||
                             pattern.charAt ( j ) == '\u0644'   )
                            root.append ( word.charAt ( j ) );

                    modifiedWord = root.toString ( );
                    modifiedWord = isThreeLetters ( modifiedWord );

                    if ( rootFound )
                    {
                        word = modifiedWord;
                        return word;
                    }
                }
            }
        }
        return word;
    }
    
    
    @SuppressWarnings("rawtypes")
	private static String checkForSuffixes ( String word )
    {   
        
        String suffix = "";
        String modifiedWord = word;
        Vector suffixes = ( Vector ) staticFiles.elementAt ( 14 );
        fromSuffixes = true;

        // for every suffix in the list
        for ( int i = 0; i < suffixes.size ( ); i++ )
        {
            suffix = ( String ) suffixes.elementAt ( i );

            // if the suffix is found
            if( suffix.regionMatches ( 0, modifiedWord, modifiedWord.length ( ) - suffix.length ( ), suffix.length ( ) ) )
            {
                modifiedWord = modifiedWord.substring ( 0, modifiedWord.length ( ) - suffix.length ( ) );

                // if the word is a stopword
                if ( checkStopwords ( modifiedWord ) )
                {
                    fromSuffixes = false;
                    return modifiedWord;
                }
                if ( modifiedWord.length ( ) == 2 )
                {
                    modifiedWord = isTwoLetters ( modifiedWord );
                }
                else if ( modifiedWord.length ( ) == 3 )
                {
                    modifiedWord = isThreeLetters ( modifiedWord );
                }
                else if ( modifiedWord.length ( ) == 4 )
                {
                    isFourLetters ( modifiedWord );
                }

                // if the root is not found, check for patterns
                if ( !rootFound && modifiedWord.length( ) > 2 )
                {
                    modifiedWord = checkPatterns( modifiedWord );
                }

                if ( stopwordFound )
                {
                    fromSuffixes = false;
                    return modifiedWord;
                }

                // if the root is found, return the modified word
                if ( rootFound )
                {
                    fromSuffixes = false;
                    return modifiedWord;
                }
            }
        }
        fromSuffixes = false;
        return word;
    }
    
    @SuppressWarnings("rawtypes")
	private static String duplicate ( String word )
    {   
        // if a letter is duplicated
        if ( ( ( Vector ) staticFiles.elementAt ( 1 ) ).contains ( word ) )
        {
            word = word + word.substring ( 1 );
            rootFound = true;
            return word;
        }
        return word;
    }
    
    // if the last letter of the word is a weak letter
    @SuppressWarnings("rawtypes")
	private static String lastWeak ( String word )
    {   
        
        StringBuffer stemmedWord = new StringBuffer ( "" );
        // if the last letter is an alif
        if ( ( ( Vector )staticFiles.elementAt(4)).contains (word))
        {
            stemmedWord.append ( word );
            stemmedWord.append ( "\u0627" );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;
            
            return word;
        }
        // if the last letter is an hamza
        else if ( ( ( Vector ) staticFiles.elementAt ( 5 ) ) .contains ( word ) )
        {
            stemmedWord.append ( word );
            stemmedWord.append ( "\u0623" );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        else if ( ( ( Vector ) staticFiles.elementAt ( 6 ) ) .contains ( word ) )
        {
            stemmedWord.append ( word );
            stemmedWord.append ( "\u0649" );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        else if ( ( ( Vector ) staticFiles.elementAt ( 7 ) ).contains ( word ) )
        {
            stemmedWord.append ( word );
            stemmedWord.append ( "\u064a" );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        return word;
    }

    // if the first letter is a weak letter
    @SuppressWarnings("rawtypes")
	private static String firstWeak ( String word )
    {   
        StringBuffer stemmedWord = new StringBuffer ( "" );
        // if the firs letter is a waaw
        if( ( ( Vector ) staticFiles.elementAt ( 2 ) ) .contains ( word ) )
        {
            stemmedWord.append ( "\u0648" );
            stemmedWord.append ( word );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        //if the first letter is a yaa
        else if ( ( ( Vector ) staticFiles.elementAt ( 3 ) ) .contains ( word ) )
        {
            stemmedWord.append ( "\u064a" );
            stemmedWord.append ( word );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
    return word;
    }

    // if the middle letter of the root is weak
    @SuppressWarnings("rawtypes")
	private static String middleWeak ( String word )
    {   
        StringBuffer stemmedWord = new StringBuffer ( "j" );
        // if the middle letter is a waaw
        if ( ( ( Vector ) staticFiles.elementAt ( 8 ) ) .contains ( word ) )
        {
            stemmedWord.setCharAt ( 0, word.charAt ( 0 ) );
            stemmedWord.append ( "\u0648" );
            stemmedWord.append ( word.substring ( 1 ) );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        // if the middle letter is a yaa
        else if ( ( ( Vector ) staticFiles.elementAt ( 9 ) ) .contains ( word ) )
        {
            stemmedWord.setCharAt ( 0, word.charAt ( 0 ) );
            stemmedWord.append ( "\u064a" );
            stemmedWord.append ( word.substring ( 1 ) );
            word = stemmedWord.toString ( );
            stemmedWord.setLength ( 0 );
            rootFound = true;

            return word;
        }
        return word;
    }
    
    // remove the definite article
    @SuppressWarnings("rawtypes")
	private static String checkDefiniteArticle ( String word )
    {   
        String definiteArticle = "";
        String modifiedWord = "";
        Vector definiteArticles = ( Vector ) staticFiles.elementAt ( 0 );

        // for every definite article in the list
        for ( int i = 0; i < definiteArticles.size ( ); i++ )
        {
            definiteArticle = ( String ) definiteArticles.elementAt ( i );
            if ( definiteArticle.regionMatches ( 0, word, 0, definiteArticle.length ( ) ) )
            {
                // remove the definite article
                modifiedWord = word.substring ( definiteArticle.length ( ), word.length ( ) );

                if ( checkStopwords ( modifiedWord ) )
                    return modifiedWord;

                if ( modifiedWord.length ( ) == 2 )
                    modifiedWord = isTwoLetters ( modifiedWord );
                else if ( modifiedWord.length ( ) == 3 && !rootFound )
                    modifiedWord = isThreeLetters ( modifiedWord );
                else if ( modifiedWord.length ( ) == 4 )
                    isFourLetters ( modifiedWord );

                if ( !rootFound && modifiedWord.length ( ) > 2 )
                    modifiedWord = checkPatterns ( modifiedWord );

                if ( !rootFound && !stopwordFound )
                {
                    modifiedWord = checkForSuffixes ( modifiedWord );
                }

                if ( !rootFound && !stopwordFound )
                {
                    modifiedWord = checkForPrefixes ( modifiedWord );
                }


                if ( stopwordFound )
                    return modifiedWord;

                if ( rootFound && !stopwordFound )
                {
                    return modifiedWord;
                }
            }
        }
        if ( modifiedWord.length ( ) > 3 )
            return modifiedWord;
        return word;
    }
    
    // remove the special prefix (waaw)
    private static String checkPrefixWaw ( String word )
    {   
        
        String modifiedWord = "";

        if ( word.length ( ) > 3 && word.charAt ( 0 ) == '\u0648' )
        {
            modifiedWord = word.substring ( 1 );

            if ( checkStopwords ( modifiedWord ) )
                return modifiedWord;

            if ( modifiedWord.length ( ) == 2 )
                modifiedWord = isTwoLetters( modifiedWord );
            else if ( modifiedWord.length ( ) == 3 && !rootFound )
                modifiedWord = isThreeLetters( modifiedWord );
            else if ( modifiedWord.length ( ) == 4 )
                isFourLetters ( modifiedWord );

            if ( !rootFound && modifiedWord.length ( ) > 2 )
                modifiedWord = checkPatterns ( modifiedWord );

            if ( !rootFound && !stopwordFound )
            {
                modifiedWord = checkForSuffixes ( modifiedWord );
            }

            if ( !rootFound && !stopwordFound )
            {
                modifiedWord = checkForPrefixes ( modifiedWord );
            }

            if ( stopwordFound )
                return modifiedWord;

            if ( rootFound && !stopwordFound )
            {
                return modifiedWord;
            }
        }
        return word;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected static boolean addVectorFromFile ( String fileName )
    {   
        
        boolean returnValue;
        try
        {
            Vector vectorFromFile = new Vector ( );

            File file = new File ( fileName );
            FileInputStream fileInputStream = new FileInputStream ( file );
            InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream, "UTF-16" );
            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader, 20000 );

            String part;
            StringBuffer word = new StringBuffer ( );
            while ( ( part = bufferedReader.readLine ( ) ) != null )
            {
                part = part + "  ";
                for ( int index = 0; index < part.length ( ) - 1; index ++ )
                {
                    if ( ! ( Character.isWhitespace ( part.charAt ( index ) ) ) )
                    {
                        word.append ( part.charAt ( index ) );
                    }
                    else
                    {
                        if ( word.length ( ) != 0 )
                        {
                            vectorFromFile.addElement ( word.toString ( ) );
                            word.setLength ( 0 );
                        }
                    }
                }
            }

            vectorFromFile.trimToSize ( );
            bufferedReader.close ( );
   	        fileInputStream.close ( );
            staticFiles.addElement ( vectorFromFile );
            returnValue = true;
        }
        catch ( Exception exception )
        {
            returnValue = false;
        }
        return returnValue;
    }
    

}
