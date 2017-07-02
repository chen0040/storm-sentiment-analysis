package com.github.chen0040.sentiment;


import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by xschen on 2/7/2017.
 */
public class ConsoleOutput extends BaseFilter {
   private AtomicInteger truePositive = new AtomicInteger(0);
   private AtomicInteger trueNegative = new AtomicInteger(0);
   private AtomicInteger falsePositive = new AtomicInteger(0);
   private AtomicInteger falseNegative = new AtomicInteger(0);

   @Override public boolean isKeep(TridentTuple tuple) {
      Boolean prediction = tuple.getBooleanByField("prediction");
      Boolean actual = tuple.getBooleanByField("label");

      if(prediction && actual) {
         truePositive.incrementAndGet();
      } else if(!prediction && !actual) {
         trueNegative.incrementAndGet();
      } else if(prediction && !actual) {
         falsePositive.incrementAndGet();
      } else {
         falseNegative.incrementAndGet();
      }

      String comment=tuple.getStringByField("text");

      System.out.println(comment);
      System.out.println(actual + " >> " + prediction);
      return true;
   }

   @Override
   public void cleanup() {
      int total = trueNegative.get() + truePositive.get() + falseNegative.get() + falsePositive.get();
      double accuracy = (double)(truePositive.get() + trueNegative.get()) / total;
      double precision = (double)truePositive.get() / (truePositive.get() + falsePositive.get());
      double recall = (double)truePositive.get() / (truePositive.get() + falseNegative.get());
      System.out.println("accuracy: " + accuracy);
      System.out.println("precision: " + precision);
      System.out.println("recall: " + recall);
   }
}
