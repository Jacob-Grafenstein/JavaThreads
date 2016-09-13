/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtrial;

import java.util.concurrent.Semaphore;
/**
 *
 * @author jake
 */
public class ThreadTrial {
    
    static Semaphore mutex = new Semaphore(1);
    static int myMatrix[][];
    public static final int HOURS = 24;

    static class Incrementer implements Runnable {
        static int threadID;
        static String threadName;
        public Thread myThread;
        
        Incrementer(int id) {
            this.threadID = id;
            this.threadName = "Incrementer " + new Integer(id).toString();
        }
        
        @Override
        public void run() {
            System.out.println(threadName + " running.");
            for (int i = 0; i < HOURS; i++) {
                for (int j = 0; j < HOURS; j++) {
                    try {
                        mutex.acquire();
                        System.out.println(threadName + " incrementing matrix " + " at i=" + i + " and j=" + j);
                        myMatrix[i][j]++;
                        mutex.release();
                    } catch (Exception e) {
                        System.out.println("DANGER");
                    }
                }   
            } 
        }
        
        public void start() {
            System.out.println("Starting thread " + threadID);
            if (myThread == null) {
                myThread = new Thread(this, threadName);
                myThread.start();
            }
        }
    }
    
    static class Chooser implements Runnable {
        static int threadID;
        static String threadName;
        public Thread myThread;
        
        Chooser(int id) {
            this.threadID = id;
            this.threadName = "Chooser " + new Integer(id).toString();
        }
        
        @Override
        public void run() {
            System.out.println(threadName + " running.");
            for (int i = 0; i < HOURS; i++) {
                for (int j = 0; j < HOURS; j++) {
                    try {
                        mutex.acquire();
                        System.out.println(threadName + "Retrieving: " + myMatrix[i][j] + " at i=" + i + " and j=" + j);
                        mutex.release();
                    } catch (Exception e) {
                        System.out.println("DANGER");
                    }
                }
            }
        }
        
        public void start() {
            System.out.println("Starting thread " + threadID);
            if (myThread == null) {
                myThread = new Thread(this, threadName);
                myThread.start();
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        myMatrix = new int[HOURS][HOURS];
        for (int i = 0; i < HOURS; i++) {
            for (int j = 0; j < HOURS; j++) {
                myMatrix[i][j] = 0;
            }
        }
        Incrementer firstThread = new Incrementer(1);
        Chooser secondThread = new Chooser(1);
        firstThread.start();
        secondThread.start();
    }
    
}
