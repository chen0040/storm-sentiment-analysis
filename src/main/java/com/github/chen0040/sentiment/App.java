package com.github.chen0040.sentiment;



import com.github.pmerienne.trident.ml.nlp.TwitterSentimentClassifier;

import storm.trident.TridentTopology;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LocalCluster cluster=new LocalCluster();
        Config config=new Config();
        
        cluster.submitTopology("TridentWriteToESDemo", config, buildTopology());
        
        try{
        	Thread.sleep(10000);
        }catch(InterruptedException ex)
        {
        	
        }
        
        cluster.killTopology("TridentWriteToESDemo");
        cluster.shutdown();
    }
    
    private static StormTopology buildTopology()
    {
    	TridentTopology topology=new TridentTopology();
    	
    	TweetCommentSpout spout=new TweetCommentSpout();
    	
    	topology.newStream("classifyAndIndex", spout)
                .each(new Fields("text"), new TwitterSentimentClassifier(), new Fields("prediction"))
                .each(new Fields("text", "prediction"), new CreateESIndex());
    	
    	return topology.build();
    }
}
