package concurrent.io;

import java.io.*;

public final class SaveResultToFile implements Output {
    private final File file;

    public SaveResultToFile(final File file) {
        this.file = file;
    }

    @Override
    public void saveContent(final String content) {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
