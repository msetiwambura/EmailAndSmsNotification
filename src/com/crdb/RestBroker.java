package com.crdb;

import org.apache.commons.io.IOUtils;

import java.io.*;

import org.apache.commons.codec.binary.Base64;

import java.net.HttpURLConnection;
import java.net.URL;

public class RestBroker {
    public String server = "";
    public String port = "";
    public String smsServer = "";
    public String smsPort = "";

    public RestBroker(SendEmailNotification parent) {
        this.server = parent.server;
        this.port = parent.port;
        this.smsServer = parent.smsServer;
        this.smsPort = parent.smsPort;
    }

    public String getRequestBroker(String uri) throws IOException {
        String locationUrl = server + ":" + port + uri;
        InforLogger inforLogger = new InforLogger();
        String result = "";
        try {
            URL url = new URL(locationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String userpass = "Admin" + ":" + "Admin_1234";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setConnectTimeout(500);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Authorization", "Basic Q3JlZGl0cHJvOlowckBjbGUwMDE=");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("X-API-KEY", "CREDITPRO@DML06_005");
            connection.setRequestProperty("X-HTTP-Method-Override", "GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream stream = connection.getOutputStream();
            stream.close();
            //read response
            InputStream inputStream;
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            result = IOUtils.toString(inputStream, "UTF-8");
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST && connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inforLogger.LogTissInfo(result);
            } else {
                inforLogger.LogTissError(result);
            }
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            inforLogger.LogTissError("response to the request==={" + e.getMessage() + "}");
        }
        return result;
    }

    public void postRequestBroker(String uri, String jsonInput) {
        String locationUrl = server + ":" + port + uri;
        InforLogger inforLogger = new InforLogger();
        try {
            URL url = new URL(locationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String userpass = "Admin" + ":" + "Admin_1234";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("X-API-KEY", "CREDITPRO@DML06_005");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(500);
            OutputStream stream = connection.getOutputStream();
            stream.write(jsonInput.getBytes("UTF-8"));
            stream.close();
            //read response
            InputStream inputStream;
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            String result = IOUtils.toString(inputStream, "UTF-8");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inforLogger.LogTissInfo(connection.getResponseCode() + ":" + connection.getResponseMessage() + "}");
            } else {
                inforLogger.LogTissError(connection.getResponseCode());
            }
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            inforLogger.LogTissError("response to the request==={" + e.getMessage() + "}");
        }
    }

    public void postSmsRequestBroker(String uri, String jsonInput) {
        String locationUrl = smsServer + ":" + smsPort + uri;
        InforLogger inforLogger = new InforLogger();
        try {
            URL url = new URL(locationUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String userpass = "Admin" + ":" + "Admin_1234";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("X-API-KEY", "CREDITPRO@DML06_005");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setDoOutput(true);
            connection.setConnectTimeout(500);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            stream.write(jsonInput.getBytes("UTF-8"));
            stream.close();
            //read response
            InputStream inputStream;
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            String result = IOUtils.toString(inputStream, "UTF-8");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inforLogger.LogTissInfo(connection.getResponseCode() + ":" + connection.getResponseMessage() + "}");
            } else {
                inforLogger.LogTissError(connection.getResponseCode());
            }
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            inforLogger.LogTissError("response to the request==={" + e.getMessage() + "}");
        }
    }

}
