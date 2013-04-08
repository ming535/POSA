
public class threads_ConditionVariable implements Runnable {
    private String message;
    static Object obj = new Object();

    public threads_ConditionVariable(String msg) {
        this.message = msg;
    }

    public void run() {

        synchronized (obj) {
            try {
                if (message == "Ping!") {
                    for (int i = 0; i < 3; i++) {
                        obj.wait();
                        System.out.println(message);
                        obj.notify();
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        obj.notify();
                        obj.wait();
                        System.out.println(message);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new threads_ConditionVariable("Ping!"));
        Thread t2 = new Thread(new threads_ConditionVariable("Pong!"));

        System.out.println("Ready... Set... Go!");
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done!");
        }
    }
} 
