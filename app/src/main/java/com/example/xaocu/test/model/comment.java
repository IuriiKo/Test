package com.example.xaocu.test.model;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class Comment {
  private int postId;
  private int id;
  private String name;
  private String email;
  private String body;

  public int getPostId() {
    return postId;
  }

  public void setPostId(int posteId) {
    this.postId = posteId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
