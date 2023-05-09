package org.project;

public class keep_aliveClient implements Runnable {



    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(10000);
                System.out.println("Keep alive");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
