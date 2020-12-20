package execution;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

public class TweetMetricsTest {
    @Test
    public void tweetMetricsTest() {
        TweetMetrics tweetMetrics = new TweetMetrics();
        
        tweetMetrics.tallyHashtag("A");
        tweetMetrics.tallyHashtag("B");
        tweetMetrics.tallyHashtag("C");
        tweetMetrics.tallyHashtag("D");
        tweetMetrics.tallyHashtag("E");
        tweetMetrics.tallyHashtag("B");

        List<String> topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "B");
        assertEquals(topFive.get(1), "A");
        assertEquals(topFive.get(2), "C");
        assertEquals(topFive.get(3), "D");
        assertEquals(topFive.get(4), "E");
    }

    @Test
    public void tweetMetricsTest_noDoubleTracking() {
        TweetMetrics tweetMetrics = new TweetMetrics();
        
        tweetMetrics.tallyHashtag("A");
        tweetMetrics.tallyHashtag("A");

        List<String> topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "A");
        assertEquals(topFive.get(1), null);
        assertEquals(topFive.get(2), null);
        assertEquals(topFive.get(3), null);
        assertEquals(topFive.get(4), null);
    }

    @Test
    public void tweetMetricsTest2() {
        TweetMetrics tweetMetrics = new TweetMetrics();
        
        tweetMetrics.tallyHashtag("A");

        List<String> topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "A");
        assertEquals(topFive.get(1), null);
        assertEquals(topFive.get(2), null);
        assertEquals(topFive.get(3), null);
        assertEquals(topFive.get(4), null);

        tweetMetrics.tallyHashtag("B");
        tweetMetrics.tallyHashtag("B");

        topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "B");
        assertEquals(topFive.get(1), "A");
        assertEquals(topFive.get(2), null);
        assertEquals(topFive.get(3), null);
        assertEquals(topFive.get(4), null);
        
        tweetMetrics.tallyHashtag("C");
        tweetMetrics.tallyHashtag("C");
        tweetMetrics.tallyHashtag("C");

        topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "C");
        assertEquals(topFive.get(1), "B");
        assertEquals(topFive.get(2), "A");
        assertEquals(topFive.get(3), null);
        assertEquals(topFive.get(4), null);

        tweetMetrics.tallyHashtag("D");
        tweetMetrics.tallyHashtag("D");
        tweetMetrics.tallyHashtag("D");
        tweetMetrics.tallyHashtag("D");

        topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "D");
        assertEquals(topFive.get(1), "C");
        assertEquals(topFive.get(2), "B");
        assertEquals(topFive.get(3), "A");
        assertEquals(topFive.get(4), null);

        tweetMetrics.tallyHashtag("E");
        tweetMetrics.tallyHashtag("E");
        tweetMetrics.tallyHashtag("E");
        tweetMetrics.tallyHashtag("E");
        tweetMetrics.tallyHashtag("E");

        topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "E");
        assertEquals(topFive.get(1), "D");
        assertEquals(topFive.get(2), "C");
        assertEquals(topFive.get(3), "B");
        assertEquals(topFive.get(4), "A");

        tweetMetrics.tallyHashtag("F");
        tweetMetrics.tallyHashtag("F");
        tweetMetrics.tallyHashtag("F");
        tweetMetrics.tallyHashtag("F");
        tweetMetrics.tallyHashtag("F");
        tweetMetrics.tallyHashtag("F");

        topFive = tweetMetrics.getTopFiveAsList();
        assertEquals(topFive.get(0), "F");
        assertEquals(topFive.get(1), "E");
        assertEquals(topFive.get(2), "D");
        assertEquals(topFive.get(3), "C");
        assertEquals(topFive.get(4), "B");
    }

}