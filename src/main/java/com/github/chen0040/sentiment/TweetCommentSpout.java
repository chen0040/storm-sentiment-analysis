package com.github.chen0040.sentiment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.github.pmerienne.trident.ml.core.TextInstance;
import com.github.pmerienne.trident.ml.preprocessing.EnglishTokenizer;
import com.github.pmerienne.trident.ml.preprocessing.TextTokenizer;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;

public class TweetCommentSpout implements IBatchSpout {
	
	private static final long serialVersionUID = 1L;
	private static List<List<Object>> data=new ArrayList<List<Object>>();

	private int batchIndex;
	private int batchSize=10;
	
	static{
		BufferedReader br=null;
		InputStream is=null;
		String filePath="twitter-sentiment.csv";
		try {
			is=TweetCommentSpout.class.getClassLoader().getResourceAsStream(filePath);
			br=new BufferedReader(new InputStreamReader(is));
			String line=null;
			while((line=br.readLine())!=null)
			{
				String[] values = line.split(",");
				Boolean label=Integer.parseInt(values[0]) == 1;
				String text=values[1];
//				TextTokenizer tokenizer=new EnglishTokenizer();
//				List<String> tokens = tokenizer.tokenize(text);
//				TextInstance<Integer> instance=new TextInstance<Integer>(label, tokens);
				data.add(new Values(text, label));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	public void open(Map conf, TopologyContext context) {

	}

	public void emitBatch(long batchId, TridentCollector collector) {
		int maxBatchCount = data.size() / batchSize;
		if(maxBatchCount > 0 && batchIndex < maxBatchCount)
		{
			for(int i=(batchSize * batchIndex); i < data.size() && i < (batchIndex+1) * batchSize; ++i)
			{
				collector.emit(data.get(i));
			}
			batchIndex++;
		}
	}

	public void ack(long batchId) {

		
	}

	public void close() {

		
	}

	public Map getComponentConfiguration() {
		return null;
	}

	public Fields getOutputFields() {
		return new Fields("text", "label");
	}
	
}
