package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

public class WgetURL implements Runnable {
    private final String url;
    private final int speed;

    public WgetURL(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long pauseTime, time1, time2, timeStart, timeEnd;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int bytesDownload = 0;
            timeStart = Calendar.getInstance().getTimeInMillis();
            time1 = Calendar.getInstance().getTimeInMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {

                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesDownload += bytesRead;

                if (bytesDownload >= speed) {
                    time2 = Calendar.getInstance().getTimeInMillis();
                    pauseTime = bytesDownload * 1000 / speed - (time2 - time1);
                    Thread.sleep(pauseTime);

                    time1 = Calendar.getInstance().getTimeInMillis();
                    bytesDownload = 0;
                }

            }

            timeEnd = Calendar.getInstance().getTimeInMillis();
            System.out.printf("Общее время: %d", (timeEnd - timeStart) / 1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        //String url = args[0];
        //int speed = Integer.parseInt(args[1]);
        long pauseTime, time1, time2;
        String url = "https://proof.ovh.net/files/10Mb.dat";
        int speed = 1024 * 1024;
        Thread wget = new Thread(new WgetURL(url, speed));
        wget.start();
        wget.join();
    }
}
