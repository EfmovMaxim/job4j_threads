package concurrent;

public class Wget {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 101; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("\rLoading : " + i  + "%");
            }
        } );

        thread.start();

        Thread.sleep(3000);
    }
}
