package execution;

import data.TweetMetrics;

public class DisplayTweetMetricsTask implements Runnable {
    private TweetMetrics tweetMetrics;

    public DisplayTweetMetricsTask(TweetMetrics tweetMetrics) {
        this.tweetMetrics = tweetMetrics;
    }
    
    @Override
    public void run() {
        System.out.println(tweetMetrics.toString() + System.lineSeparator());
    }
}