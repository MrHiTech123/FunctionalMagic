package runecraft.encoding;

import java.nio.charset.StandardCharsets;

public class EncodingSetter {
    public static void set() {
        System.out.println( StandardCharsets.UTF_16.displayName());
        System.setProperty("file.encoding", StandardCharsets.UTF_32.displayName());
    }
}
