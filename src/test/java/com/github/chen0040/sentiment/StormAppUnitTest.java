package com.github.chen0040.sentiment;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import com.github.pmerienne.trident.ml.nlp.TwitterSentimentClassifier;
import org.testng.annotations.Test;
import storm.trident.TridentTopology;

import java.io.IOException;


/**
 * Created by xschen on 2/7/2017.
 */
public class StormAppUnitTest {

   @Test
   public void testStorm(){
      LocalCluster cluster=new LocalCluster();
      Config config=new Config();

      cluster.submitTopology("TridentWriteToESDemo", config, buildTopology());

      try{
         Thread.sleep(30000);
      }catch(InterruptedException ex)
      {

      }


      try {
         cluster.killTopology("TridentWriteToESDemo");
         cluster.shutdown();
      }catch(Exception exception) {

      }
   }

   private static StormTopology buildTopology()
   {
      TridentTopology topology=new TridentTopology();

      TweetCommentSpout spout=new TweetCommentSpout();

      topology.newStream("sentiment", spout)
              .each(new Fields("text"), new TwitterSentimentClassifier(), new Fields("prediction"))
              .each(new Fields("text", "prediction", "label"), new ConsoleOutput());

      return topology.build();
   }
}
