package concurrent;

public class ConsoleProgress implements Runnable {
    private String[] process = {"\\", "|", "/"};

    @Override
    public void run() {
        int i = 0;

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.print("\r load: " + process[i++ % 3]);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}