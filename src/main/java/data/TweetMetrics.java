package data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetMetrics {
    private long totalTweets;
    
    private Map<String, Long> hashtagCounts;

    private String[] topFiveHashTags;
    
    public TweetMetrics() {
        totalTweets = 0;
        hashtagCounts = new HashMap<>();
        topFiveHashTags = new String[5];
    }
    
    public synchronized void tallyHashtag(String hashtag) {
        long countForThisHashtag;
        if(hashtagCounts.containsKey(hashtag)) {
            countForThisHashtag = hashtagCounts.get(hashtag) + 1;
        } else {
            countForThisHashtag = 1;
        }
        hashtagCounts.put(hashtag, Long.valueOf(countForThisHashtag));
        evaluatePositionAmongTopFive(countForThisHashtag, hashtag);
    }
    
    private void evaluatePositionAmongTopFive(long count, String hashtag) {
        boolean done = false;
        String tmp;
        for(int i = 0; i < 5 && !done; i++) {
            if(topFiveHashTags[i] == null) {
                topFiveHashTags[i] = hashtag;
                done = true;
            } else if(!hashtag.equals(topFiveHashTags[i]) && count > hashtagCounts.get(topFiveHashTags[i])){
                tmp = topFiveHashTags[i];
                topFiveHashTags[i] = hashtag;
                
                for(int j = i+1; j < 5; j++) {
                    if(hashtag.equals(topFiveHashTags[j])) {
                        topFiveHashTags[j] = tmp;
                    }
                }
                done = true;
            } else if(hashtag.equals(topFiveHashTags[i])) {
                done = true;
            }
        }
    }
    
    public synchronized void incrementTotalTweets() {
        totalTweets++;
    }
    
    public String toString() {
        return String.format("Total tweets: [%d] " + System.lineSeparator() + 
            "Top five hashtags: " + System.lineSeparator() + 
            "#1 - %s" + System.lineSeparator() + "     %d occurrences" + 
            System.lineSeparator() + System.lineSeparator() + 
            "#2 - %s" + System.lineSeparator() + "     %d occurrences" + 
            System.lineSeparator() + System.lineSeparator() + 
            "#3 - %s" + System.lineSeparator() + "     %d occurrences" +
            System.lineSeparator() + System.lineSeparator() + 
            "#4 - %s" + System.lineSeparator() + "     %d occurrences" +
            System.lineSeparator() + System.lineSeparator() + 
            "#5 - %s" + System.lineSeparator() + "     %d occurrences",
            totalTweets, 
            topFiveHashTags[0], hashtagCounts.get(topFiveHashTags[0]),
            topFiveHashTags[1], hashtagCounts.get(topFiveHashTags[1]),
            topFiveHashTags[2], hashtagCounts.get(topFiveHashTags[2]),
            topFiveHashTags[3], hashtagCounts.get(topFiveHashTags[3]),
            topFiveHashTags[4], hashtagCounts.get(topFiveHashTags[4])); 
    }
    
    public List<String> getTopFiveAsList() {
        return Arrays.asList(topFiveHashTags);
    }
}