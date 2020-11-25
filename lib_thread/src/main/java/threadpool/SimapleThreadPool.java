package threadpool;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yuhongwen
 * on 2020/11/25
 *
 *
 *  1: Executor
 *    An {@code Executor} is normally used
 *    instead of explicitly creating threads. For example, rather than
 *    invoking {@code new Thread(new RunnableTask()).start()} for each
 *    of a set of tasks, you might use:
 *
 *  2: ExecutorService
 *     <p>Memory consistency effects: Actions in a thread prior to the
 *  * submission of a {@code Runnable} or {@code Callable} task to an
 *  * {@code ExecutorService}
 *
 *  3： newSingleThreadExecutor
 *      Creates an Executor that uses a single worker thread operating
 *      * off an unbounded queue. (Note however that if this single
 *      * thread terminates due to a failure during execution prior to
 *      * shutdown, a new one will take its place if needed to execute
 *      * subsequent tasks.)  Tasks are guaranteed to execute
 *      * sequentially, and no more than one task will be active at any
 *      * given time. Unlike the otherwise equivalent
 *      * {@code newFixedThreadPool(1)} the returned executor is
 *      * guaranteed not to be reconfigurable to use additional threads.
 *
 *
 */
public class SimapleThreadPool {
    private static ExecutorService sExecutor;
    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            if (submit.get()) return;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("LogUtils", "log to " + filePath + " failed!");
    }
}
