package org.voxity.api;

import com.google.api.client.util.Key;
import java.util.List;

/**
 * @author Yaniv Inbar
 */
public class ChannelsFeed {

  @Key("data")
  public List<Channel> list;
  
  @Key("status")
  public int status;
}