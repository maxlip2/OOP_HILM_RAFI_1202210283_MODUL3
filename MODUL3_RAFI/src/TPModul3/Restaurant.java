package TPModul3;

public class Restaurant implements Runnable {

    private boolean waitingForPickup = false;
    private static final Object lock = new Object();
    private static int coffeeNumber = 1;

    @Override
    public void run() {
        while (true) {
            makeCoffee();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWaitingForPickup(boolean waitingForPickup) {
        this.waitingForPickup = waitingForPickup;
    }

    public static int getCoffeeNumber() {
        return coffeeNumber;
    }

    public void makeCoffee() {
        synchronized(Restaurant.lock) {
            if (this.waitingForPickup) {
                try {
                    System.out.println("Coffee Machine: Waiting for the Waiter to deliver the coffee");
                    Restaurant.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            waitingForPickup = true;
            System.out.println("Coffee Machine:  Making Coffee Number " + coffeeNumber++);
            Restaurant.lock.notifyAll();
            System.out.println("Coffee Machine: Telling the waiter to get the coffee\n");
        }
    }

    public static Object getLock() {
        return lock;
    }
}
