package execution;

import java.text.Bidi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordTweetMetricsTask implements Runnable {
    private TweetMetrics tweetMetrics;
    private String tweet;
    
    public RecordTweetMetricsTask(TweetMetrics tweetMetrics, String tweet) {
        this.tweetMetrics = tweetMetrics;
        this.tweet = tweet;
    }
    
    @Override
    public void run() {
        JSONObject tweetEntities;
        try {
            //System.out.println(tweet.toString());
            tweetEntities = new JSONObject(tweet)  
                .getJSONObject("data")
                .optJSONObject("entities");
            if(tweetEntities != null) {
                JSONArray hashtags = tweetEntities.optJSONArray("hashtags");
                if(hashtags != null) {
                    for(int i = 0; i < hashtags.length(); i++) {
                        JSONObject hashtagObj = hashtags.getJSONObject(i);
                        if(hashtagObj != null) {
                            tweetMetrics.tallyHashtag(hashtagObj.getString("tag"));
                        }
                    }
                }
            }
        } catch(JSONException e) {
            System.out.println("JSON Exception for tweet " + tweet + ". Exception message was " + e.getMessage());
            e.printStackTrace();
        }
        tweetMetrics.incrementTotalTweets();
    }
}