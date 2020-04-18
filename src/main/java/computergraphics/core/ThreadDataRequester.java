package computergraphics.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ThreadDataRequester
 * Thread manager to request data off the main thread
 * CURRENTLY DISABLED
 */
public class ThreadDataRequester {

    public static ThreadDataRequester instance;
    private Queue<DataThread> threadQueue;

    public ThreadDataRequester() {
        instance = this;
        threadQueue = new LinkedList<DataThread>();
    }

    
    /** 
     * Run the given generate method and return the result while run on a different thread
     * @param gen The generate method
     * @param callback THe callback method
     */
    public static void GenerateData(Supplier<Object> gen, Consumer<Object> callback) {
        // Thread thread = new Thread(new Runnable(){
        
        //     @Override
        //     public void run() {
        //         instance.GenerateDataThread(gen, callback);
                
        //     }
        // });
        // thread.start();

        Object data = gen.get();
        callback.accept(data);
    }

    
    /** 
     * The actual method that is run on a seperate thread.
     * @param gen
     * @param callback
     */
    private void GenerateDataThread(Supplier<Object> gen, Consumer<Object> callback) {
        Object data = gen.get();
        synchronized(threadQueue) {
            threadQueue.add(new DataThread(callback, data));
        }
    }

    /**
     * Update method called by game loop
     * Check queue for threads that are finish and dequeue them
     */
    public void Update() {
        if(threadQueue.size() > 0) {
            for(int i = 0; i < threadQueue.size(); i++) {
                DataThread dataThread = threadQueue.poll();
                dataThread.callback.accept(dataThread.data);
            }
        }
    }

    /**
     * Holds data for the threads.
     */
    private class DataThread {
        public Consumer<Object> callback;
        public Object data;

        public DataThread(Consumer<Object> callback, Object data) {
            this.callback = callback;
            this.data = data;
        }
    }
}