package execution;

import java.util.concurrent.ThreadPoolExecutor;

import data.TweetMetrics;
import execution.RecordTweetMetricsTask;

public class DisplayTweetMetricsTask implements Runnable {
    private TweetMetrics tweetMetrics;
    
    private static ThreadPoolExecutor threadPoolExecutor;

    public DisplayTweetMetricsTask(TweetMetrics tweetMetrics) {
        this.tweetMetrics = tweetMetrics;
    }
    
    @Override
    public void run() {
        System.out.println(tweetMetrics.toString() + System.lineSeparator());// + RecordTweetMetricsTask.getAverageTaskDuration() + System.lineSeparator());
        
        if(threadPoolExecutor != null) {
            // Get the stats before you execute the tasks
            System.out.println("Core threads: " + threadPoolExecutor.getCorePoolSize());
            System.out.println("Largest number of simultaneous executions: "
                + threadPoolExecutor.getLargestPoolSize());
            System.out.println("Maximum number of  allowed threads: "
                + threadPoolExecutor.getMaximumPoolSize());
            System.out.println("Current threads in the pool: "
                + threadPoolExecutor.getPoolSize());
            System.out.println("Currently executing threads: "
                + threadPoolExecutor.getActiveCount());
            System.out.println("Total number of threads ever scheduled: "
                + threadPoolExecutor.getTaskCount());
            System.out.println("Current queue size: "
                + threadPoolExecutor.getQueue().size());
        }
    }
    
    public static void setThreadPoolExecutor(ThreadPoolExecutor aThreadPoolExecutor) {
        threadPoolExecutor = aThreadPoolExecutor;
    }
}