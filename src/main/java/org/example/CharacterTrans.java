package org.example;

import java.nio.charset.StandardCharsets;

public class CharacterTrans {


    private final byte[] data;

    public CharacterTrans(byte [] data) {
        this.data = data;
    }


    public boolean isPrintAbleASCII(){
        for(byte b : data){
            if(b < 0x20 || b > 0x7E){
                return false;
            }
        }
        return true;
    }

    public String normalizeKey(byte[] data){
        if(isPrintAbleASCII()){
            return new String(data, StandardCharsets.US_ASCII).trim();
        }else{
            return toHEX(data);
        }
    }


    public String toHEX(byte[] data){
        StringBuilder sb = new StringBuilder();
        for(byte b : data){
            sb.append(String.format("%02X ",b));
        }
        return sb.toString().trim();
    }
}
