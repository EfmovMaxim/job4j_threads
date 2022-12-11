package concurrent;

public class DCLSingleton {
    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {

    }

    public static void main(String[] args) throws InterruptedException {

        Thread tr1 = new Thread(() -> {
            System.out.println(DCLSingleton.instOf());
        });

        Thread tr2 = new Thread(() -> {
            System.out.println(DCLSingleton.instOf());
        });

        tr1.start();
        tr2.start();

        tr1.join();
        tr2.join();
    }
}
