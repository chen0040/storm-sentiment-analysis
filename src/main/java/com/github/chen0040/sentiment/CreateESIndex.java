package com.github.chen0040.sentiment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import storm.trident.operation.BaseFilter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class CreateESIndex extends BaseFilter{

	private static final long serialVersionUID = 1L;
	private int esIndex=1;
	private String wsUrl="http://127.0.0.1:9200";
	private String indexName="twittersentiment"; //must be lowercase
	private String typeName="trident";
	private CloseableHttpClient client;
	private String lastIndexedDocumentIdQueryJson="{\"query\": { \"match_all\": {}}, \"size\": 1,"+
	  "\"sort\": ["+
	    "{"+
	      "\"_timestamp\": {"+
	        "\"order\": \"desc\""+
	      "}"+
	    "}"+
	  "]"+
	"}";

	public boolean isKeep(TridentTuple tuple) {
		// TODO Auto-generated method stub
		Boolean prediction =tuple.getBooleanByField("prediction");
		String comment=tuple.getStringByField("text");
		System.out.println(comment + " >> " + prediction);
		
		if(client != null)
		{
			HttpPut method=new HttpPut(wsUrl+"/"+indexName+"/"+typeName+"/"+esIndex);
			
			Date currentTime= new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
			String dateString = format1.format(currentTime)+"T"+format2.format(currentTime);
			
			CloseableHttpResponse response=null;
			try{
				String json = "{\"text\":\""+comment+"\", \"prediction\":\""+prediction+"\", \"postTime\":\""+dateString+"\"}";
				System.out.println(json);
				StringEntity params=new StringEntity(json);
				params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				
				method.setEntity(params);
				
				method.addHeader("Accept", "application/json");
				method.addHeader("Content-type", "application/json");
				
				response = client.execute(method);
				
				HttpEntity entity=response.getEntity();
				String responseText=EntityUtils.toString(entity);
				System.out.println(responseText);
			}catch(IOException ex) {
				ex.printStackTrace();
			}finally {
				method.releaseConnection();
			}
			esIndex++;
		}
		
		return true;
	}
	
	@Override
    public void prepare(Map conf, TridentOperationContext context) {
		
		client=HttpClients.custom().setRetryHandler(new MyRetryHandler()).build();
		
		CloseableHttpResponse response=null;
		HttpDelete method=new HttpDelete(wsUrl+"/"+indexName);
		try{
			response = client.execute(method);
			HttpEntity entity=response.getEntity();
			String responseBody=EntityUtils.toString(entity);
			System.out.println(responseBody);
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
    }
	
	private class MyRetryHandler implements HttpRequestRetryHandler {

		public boolean retryRequest(IOException arg0, int arg1, HttpContext arg2) {
			// TODO Auto-generated method stub
			return false;
		}
	}

    @Override
    public void cleanup() {
    	try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
