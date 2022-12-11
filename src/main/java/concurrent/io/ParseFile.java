package concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output.toString();
    }

    public String getAllContent() {
        return getContent(c -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(c -> c < 0x08);
    }
}