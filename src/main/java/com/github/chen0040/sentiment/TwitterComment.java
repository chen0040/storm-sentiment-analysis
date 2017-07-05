package com.github.chen0040.sentiment;


/**
 * Created by xschen on 5/7/2017.
 */
public class TwitterComment {
   private String text;
   private boolean prediction;
   private boolean label;
   private String postTime;


   public String getText() {
      return text;
   }


   public void setText(String text) {
      this.text = text;
   }


   public boolean isPrediction() {
      return prediction;
   }


   public void setPrediction(boolean prediction) {
      this.prediction = prediction;
   }


   public boolean isLabel() {
      return label;
   }


   public void setLabel(boolean label) {
      this.label = label;
   }


   public String getPostTime() {
      return postTime;
   }


   public void setPostTime(String postTime) {
      this.postTime = postTime;
   }
}
