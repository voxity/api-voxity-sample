package org.voxity.api;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/**
 * URL for Dailymotion API.
 *
 * @author Yaniv Inbar
 */
public class VoxityUrl extends GenericUrl {

  @Key
  private String fields;

  public VoxityUrl(String encodedUrl) {
    super(encodedUrl);
  }

  /**
   * @return the fields
   */
  public String getFields() {
    return fields;
  }

  /**
   * @param fields the fields to set
   */
  public void setFields(String fields) {
    this.fields = fields;
  }
}