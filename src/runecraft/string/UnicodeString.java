package runecraft.string;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UnicodeString {
    public static String convert(String inString) {
        return new String(inString.getBytes(StandardCharsets.UTF_8), Charset.forName("UTF-32"));
    }
}
