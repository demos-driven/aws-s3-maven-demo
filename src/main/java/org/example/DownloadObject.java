/**
 * Copyright (C) 2021 Urban Compass, Inc.
 */
package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class DownloadObject {

  static final String BUCKET_NAME = "dip.doc-classification.indico.pdf-normal";

  static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
      .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
      .withRegion(Regions.US_EAST_1)
      .build();

  public static void main(String[] args) {
    final String keyName = "Xnip2021-12-13_10-38-56.png";

    System.out.format("Downloading %s from S3 bucket %s...\n", keyName, BUCKET_NAME);
    try {
      S3Object s3Object = s3.getObject(BUCKET_NAME, keyName);
      S3ObjectInputStream s3is = s3Object.getObjectContent();
      FileOutputStream fos = new FileOutputStream(new File(keyName));

      byte[] readBuf = new byte[1024];
      int readLen = 0;
      while ((readLen = s3is.read(readBuf)) > 0) {
        fos.write(readBuf, 0, readLen);
      }
      s3is.close();
      fos.close();
    } catch (AmazonServiceException e) {
      System.err.println(e.getErrorMessage());
      System.exit(1);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    System.out.println("Done!");
  }
}
