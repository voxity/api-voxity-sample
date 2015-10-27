package org.voxity.api;

import com.google.api.client.util.Key;

/**
 * @author Yaniv Inbar
 */
public class Channel {

  @Key
  public String id;

  @Key
  public String caller_num;

  @Key
  public int channel_state;
}