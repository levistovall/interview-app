package app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.json.JSONException;

import config.InterviewAppConfiguration;
import data.TweetMetrics;
import execution.DisplayTweetMetricsTask;
import execution.RecordTweetMetricsTask;

/*
 * Sample code to demonstrate the use of the Sampled Stream endpoint
 * */
public class InterviewApp {
  private static InterviewAppConfiguration config;

  private static TweetStreamer tweetStreamer;
  private static TweetMetrics tweetMetrics;

  private static ScheduledExecutorService displayThreadService;
  private static ExecutorService recordingThreadService;

  public static void main(String args[]) throws IOException, URISyntaxException {
    initialize();
    
    startDisplayingMetrics();
    startRecordingTweetMetrics();
  }
  
  private static void initialize() throws IOException {
    config = new InterviewAppConfiguration();
    config.load();
    
    tweetStreamer = new TweetStreamer(config);
    
    tweetMetrics = new TweetMetrics();
    
    displayThreadService = Executors.newScheduledThreadPool(1);
    
    recordingThreadService = Executors.newCachedThreadPool();
  }

  /*
   * This method calls the sample stream endpoint and streams Tweets from it
   */
  private static void startRecordingTweetMetrics() throws IOException, URISyntaxException {
    tweetStreamer.startStreamingTweets();
    String tweet = tweetStreamer.getNextTweet();
    while(tweet != null) {
      if(tweet.length() > 0) {
        recordingThreadService.submit(new RecordTweetMetricsTask(tweetMetrics, tweet));
      }
      tweet = tweetStreamer.getNextTweet();
    }
  }
  
  private static void startDisplayingMetrics() {
    displayThreadService.scheduleAtFixedRate(
      new DisplayTweetMetricsTask(tweetMetrics), 
        config.getDisplayInitialDelay(), 
        config.getDisplayIntervalSeconds(), 
        TimeUnit.SECONDS);
  }
}