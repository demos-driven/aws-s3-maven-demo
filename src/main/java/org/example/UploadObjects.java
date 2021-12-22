/**
 * Copyright (C) 2021 Urban Compass, Inc.
 */
package org.example;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class UploadObjects {

  static final String BUCKET_NAME = "dip.doc-classification.indico.pdf-normal";

  static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
      .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
      .withRegion(Regions.US_EAST_1)
      .build();

  public static void main(String[] args) throws URISyntaxException {
    URL url = UploadObjects.class.getClassLoader().getResource("pdf-normal");
    File[] files = new File(url.toURI()).listFiles();

    for (File file : files) {
      System.out.format("Uploading %s to S3 bucket %s...\n", file.getPath(), BUCKET_NAME);

      try {
        // TODO PDF 上传后的文件乱码...
        s3.putObject(BUCKET_NAME, file.getName(), new File(file.getPath()));
        System.out.println("Done!");
      } catch (AmazonServiceException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
}
