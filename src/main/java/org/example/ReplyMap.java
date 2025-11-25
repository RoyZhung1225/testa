package org.example;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ReplyMap {


    @Getter
    @Setter
    Map<String, String> replyMap = new HashMap<>();
    boolean isEnabled = false;

    public ReplyMap(){}

    public String reply (String s){

        if(!replyMap.containsKey(s)){return null;}
        return replyMap.get(s);
    }

    public void addReply(String request, String reply){
        replyMap.put(request,reply);
    }

    public void removeReply(String request){
        replyMap.remove(request);
    }

    public void setEnabled(){
        isEnabled = true;
    }
    public void setDisabled(){
        isEnabled = false;
    }

}
