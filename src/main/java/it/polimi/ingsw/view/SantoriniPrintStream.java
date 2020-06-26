package it.polimi.ingsw.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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


    SantoriniPrintStream(OutputStream out, boolean useUnicode) throws UnsupportedEncodingException {
        super(out, true, "UTF-8");
        this.useUnicode = useUnicode;
    }


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
