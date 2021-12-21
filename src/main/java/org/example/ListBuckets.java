/**
 * Copyright (C) 2021 Urban Compass, Inc.
 */
package org.example;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

/**
 * Solve exception 'failed to parse XML': https://github.com/aws/aws-sdk-java/issues/460#issuecomment-998778180
 */
public class ListBuckets {

  public static void main(String[] args) {
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .withRegion(Regions.US_EAST_1)
        .build();

    System.out.println("Your Amazon S3 buckets are:");

    for (Bucket b : s3.listBuckets()) {
      System.out.println("* " + b.getName());
    }
  }
}
