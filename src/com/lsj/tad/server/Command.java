package com.lsj.tad.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class Command {
	public static String pid(){
		String pid = ManagementFactory.getRuntimeMXBean().getName();  
        int indexOf = pid.indexOf('@');  
        if (indexOf > 0)  
        {  
            pid = pid.substring(0, indexOf);  
        }
        return pid;
	}
	
	public static String exeCmd(String commandStr) {  
        BufferedReader br = null;  
        String res = null;
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            }  
            res = new String(sb);
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }            
        }
        return res;
    }
}
