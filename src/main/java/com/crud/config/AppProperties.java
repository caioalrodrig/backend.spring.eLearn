package com.crud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Auth auth = new Auth();

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

  public Auth getAuth() {
    return auth;
  }

}