package com.marcomarchionni.strategistapi.util;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.JwtServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AdminTokenGenerator {

  public static void main(String[] args) {
    String[] possiblePaths = {
      "src/main/resources/application-default.properties",
      "strategist-api/src/main/resources/application-default.properties",
      "../strategist-api/src/main/resources/application-default.properties",
      "../../strategist-api/src/main/resources/application-default.properties"
    };

    FileInputStream fileInputStream = null;
    Properties properties = new Properties();

    for (String path : possiblePaths) {
      try {
        File file = new File(path);
        if (file.exists()) {
          System.out.println("Found properties file at: " + file.getAbsolutePath());
          fileInputStream = new FileInputStream(file);
          properties.load(fileInputStream);
          break;
        }
      } catch (Exception e) {
        System.out.println("Tried path: " + path + " - not found");
      }
    }

    if (fileInputStream == null) {
      System.err.println("Could not find application-default.properties file. Tried paths:");
      for (String path : possiblePaths) {
        System.err.println("  - " + new File(path).getAbsolutePath());
      }
      return;
    }

    try {
      String jwtSecret = properties.getProperty("token.signing.key");
      if (jwtSecret == null) {
        System.err.println("Could not find token.signing.key in properties file");
        return;
      }

      // Setup JwtService - 6 months expiration (1000ms * 60s * 60m * 24h * 180d)
      JwtService jwtService = new JwtServiceImpl(jwtSecret, 1000L * 60 * 60 * 24 * 180);

      // Setup AdminUser
      User user =
          User.builder()
              .firstName("test-admin")
              .lastName("test-admin")
              .email("test@admin.com")
              .password("test.admin")
              .role(User.Role.ADMIN)
              .accountId("U1111111")
              .build();

      // Generate token
      String token = jwtService.generateAccessToken(user);
      System.out.println("Generated token: " + token);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
