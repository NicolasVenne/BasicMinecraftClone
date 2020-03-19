package computergraphics.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ThreadDataRequester
 */
public class ThreadDataRequester {

    public static ThreadDataRequester instance;
    private Queue<DataThread> threadQueue;

    public ThreadDataRequester() {
        instance = this;
        threadQueue = new LinkedList<DataThread>();
    }

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

    private void GenerateDataThread(Supplier<Object> gen, Consumer<Object> callback) {
        Object data = gen.get();
        synchronized(threadQueue) {
            threadQueue.add(new DataThread(callback, data));
        }
    }

    public void Update() {
        if(threadQueue.size() > 0) {
            for(int i = 0; i < threadQueue.size(); i++) {
                DataThread dataThread = threadQueue.poll();
                dataThread.callback.accept(dataThread.data);
            }
        }
    }

    private class DataThread {
        public Consumer<Object> callback;
        public Object data;

        public DataThread(Consumer<Object> callback, Object data) {
            this.callback = callback;
            this.data = data;
        }
    }
}