package org.voxity.api;

import java.util.List;

import com.google.api.client.util.Key;

public class ClickToCallFeed {
	 @Key("data")
	  public List<ClickToCall> list;
	  
	  @Key("status")
	  public int status;

}
