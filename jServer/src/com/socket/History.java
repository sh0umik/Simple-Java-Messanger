/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socket;

/**
 *
 * @author fahim
 */
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class History {
    
    FileWriter file;
    FileWriter file2;
    JSONArray arr;
    JSONParser parser;
    
    Path path;
    Charset charset;
    
    public History() throws IOException {
        parser = new JSONParser();
        arr = new JSONArray();
    }
    
    public void writeHistory(Message message) throws IOException {
        try {
            
            System.out.println("Storing data");
            
            String p = message.recipient+"-chat-history.json";
            
            file = new FileWriter(p);
            JSONObject msg = new JSONObject();        

            msg.put("type", message.type);
            msg.put("sender", message.sender);
            msg.put("content", message.content);
            msg.put("reception", message.recipient);
            
            arr.add(msg);
            
            file.append(arr.toJSONString());
            file.flush();
            
            // take the unformatted json and fix
            path = Paths.get(p);
            charset = StandardCharsets.UTF_8;
            String jsonData = new String(Files.readAllBytes(path), charset);
            
            file2 = new FileWriter(p);
            jsonData = jsonData.replace("}][{", "},{");
            System.out.println("Writing : " + jsonData);
            file2.write(jsonData);
            file2.flush();
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}
