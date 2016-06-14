package com.example.daljit.androidb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RailPNRAPIClient {

    public static void main(String[] args){
        System.out.println(getPnrResponse("1234567890", "json"));
    }

    public static String getPnrResponse(String pnr, String format) {
        if(pnr == null || pnr.trim().length() != 10){
            //Some more validations like only numeric are used etc can be added.
            return "Invalid PNR.";
        }

        format = format == null || format.trim().equals("") ? "xml" : format;
        //More validations can be added like format is one of xml or json.
        String endpoint = "http://railpnrapi.com/api/check_pnr/pnr/"+pnr+"/format/"+format;

        HttpURLConnection request = null;
        BufferedReader rd = null;
        StringBuilder response = null;

        try{
            URL endpointUrl = new URL(endpoint);
            request = (HttpURLConnection)endpointUrl.openConnection();
            request.setRequestMethod("GET");
            request.connect();

            rd  = new BufferedReader(new InputStreamReader(request.getInputStream()));
            response = new StringBuilder();
            String line = null;
            while ((line = rd.readLine()) != null){
                response.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
            //e.printStackTrace();
        } catch (ProtocolException e) {
            System.out.println("Exception: " + e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            //e.printStackTrace();
        } finally {
            try{
                request.disconnect();
            } catch(Exception e){
            }

            if(rd != null){
                try{
                    rd.close();
                } catch(IOException ex){
                }
                rd = null;
            }
        }

        return response != null ? response.toString() : "No Response";
    }
}