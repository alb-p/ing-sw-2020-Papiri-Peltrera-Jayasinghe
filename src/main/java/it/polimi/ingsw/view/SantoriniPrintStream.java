package it.polimi.ingsw.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Santorini print stream.
 * It is used for those OS don't support Unicode
 */
public class SantoriniPrintStream extends PrintStream {

    boolean useUnicode;

    private static final Map<Character, Character> charMap;

    static {
        Map<Character, Character> hashEq = new HashMap<>();
        hashEq.put('è', 'e');
        hashEq.put('é', 'e');
        hashEq.put('à', 'a');
        hashEq.put('ò', 'o');
        hashEq.put('ù', 'u');
        hashEq.put('═', '-');
        hashEq.put('║', '|');
        hashEq.put('╔', '+');
        hashEq.put('╗', '+');
        hashEq.put('╚', '+');
        hashEq.put('╝', '+');
        hashEq.put('╤', '+');
        hashEq.put('╧', '+');
        hashEq.put('╩', '+');
        hashEq.put('╦', '+');
        hashEq.put('╬', '+');
        hashEq.put('╠', '+');
        hashEq.put('╣', '+');
        hashEq.put('░', '=');
        charMap = Collections.unmodifiableMap(hashEq);
    }


    /**
     * Instantiates a new Santorini print stream.
     *
     * @param out        the OutputStream
     * @param useUnicode boolean value depending on OS used
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    SantoriniPrintStream(OutputStream out, boolean useUnicode) throws UnsupportedEncodingException {
        super(out, true, "UTF-8");
        this.useUnicode = useUnicode;
    }


    /**
     * Println override.
     * If a char is in the charMap it is replace with the corresponding one
     *
     * @param s the string have to be processed
     */
    @Override
    public void println(String s) {
        if (!useUnicode) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (charMap.containsKey(s.charAt(i))) {
                    builder.append(charMap.get(s.charAt(i)));
                } else {
                    builder.append(s.charAt(i));
                }
            }
            super.println(builder.toString());
        } else {
            super.println(s);
        }
    }
    

}
