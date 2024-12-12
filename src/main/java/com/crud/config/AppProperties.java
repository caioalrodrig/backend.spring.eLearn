package com.crud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Auth auth = new Auth();
  private final App app = new App();

  public static class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;
    private String redirectUrl;

    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
      return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
      this.tokenExpirationMsec = tokenExpirationMsec;
    }

    public String getRedirectUrl() {
      return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
      this.redirectUrl = redirectUrl;
    }
  }

  public static class App{
    public String apiUrl;

    public String getApiUrl(){
      return apiUrl;
    }

    public void setRedirectUrl(String apiUrl) {
      this.apiUrl = apiUrl;
    }
  }

  public Auth getAuth() {
    return auth;
  }

  public App getApp() {
    return app;
  }

}