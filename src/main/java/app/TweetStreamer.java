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
public class TweetStreamer {

    private InterviewAppConfiguration config;
    private BufferedReader bufferedReader;
    private static final String STREAMING_ENDPOINT_WITH_ENTITIES_URL = 
        "https://api.twitter.com/2/tweets/sample/stream?tweet.fields=entities";

    public TweetStreamer(InterviewAppConfiguration config) {
        this.config = config;
    }
    
    public void startStreamingTweets() throws IOException, URISyntaxException {
        HttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();
            
        URIBuilder uriBuilder = new URIBuilder(STREAMING_ENDPOINT_WITH_ENTITIES_URL);
        
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", config.getTwitterBearerToken()));
        
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        bufferedReader = new BufferedReader(new InputStreamReader((entity.getContent())));
    }

    /*
     * This method calls the sample stream endpoint and streams Tweets from it
     */
    public String getNextTweet() throws IOException {
        return bufferedReader.readLine();
    }
}