/**
 * Copyright (C) 2021 Urban Compass, Inc.
 */
package org.example;

import java.util.List;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListObjects {

  static final String BUCKET_NAME = "dip.doc-classification.indico.pdf-normal";

  static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
      .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
      .withRegion(Regions.US_EAST_1)
      .build();

  public static void main(String[] args) {
    ListObjectsV2Result result = s3.listObjectsV2(BUCKET_NAME);

    List<S3ObjectSummary> objects = result.getObjectSummaries();

    System.out.format("Objects in S3 bucket %s:\n", BUCKET_NAME);

    for (S3ObjectSummary os : objects) {
      System.out.println("* " + os.getKey() + " " + os.getSize());
    }
  }
}
