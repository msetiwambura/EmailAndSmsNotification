package com.crdb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class EmailScheduler implements Runnable {
    InforLogger inforLogger = new InforLogger();
    public SendEmailNotification parent;
    public Thread messageSender;
    Timer timer = new Timer();

    public EmailScheduler(SendEmailNotification parent) {
        this.parent = parent;
        messageSender = new Thread(this);
        messageSender.start();
    }

    public void emailSenderScheduler() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, parent.timeInHours);
        calendar.set(Calendar.MINUTE, parent.timeInMinutes);
        calendar.set(Calendar.SECOND, parent.timeInSeconds);
        Date time = calendar.getTime();
        if (time.before(new Date())) {
            calendar.add(Calendar.DATE, 1);
            time = calendar.getTime();
        }
        timer.scheduleAtFixedRate(new RemindTask(), time, parent.timeInterval);
    }

    @Override
    public void run() {
        emailSenderScheduler();
    }

    class RemindTask extends TimerTask {
        public void run() {
            try {
                sendEmails();
                sendSms();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmails() throws IOException {
        String results = parent.rest.getRequestBroker(parent.requestData);
        if (results.contains("\"code\":200,\"message\":\"Success\"")) {
            JSONObject jsonObject = new JSONObject(results);
            JSONArray lists = jsonObject.getJSONArray("results");
            JSONObject data = new JSONObject();
            data.put("postData", lists);
            if (data.length() != 0) {
                parent.rest.postRequestBroker(parent.postData, data.toString());
            }
        }
    }

    public void sendSms() throws IOException {
        String results = parent.rest.getRequestBroker(parent.requestData);
        if (results.contains("\"code\":200,\"message\":\"Success\"")) {
            JSONObject jsonObject = new JSONObject(results);
            JSONArray lists = jsonObject.getJSONArray("results");
            JSONObject data = new JSONObject();
            JSONObject sms = new JSONObject();
            JSONArray smsData = new JSONArray();
            if (lists.length() != 0) {
                for (int i = 0; i < lists.length(); i++) {
                    JSONObject object = lists.getJSONObject(i);
                    String caseNumber = (String) object.get("CASENUMBER");
                    String userName = (String) object.get("USERFULLNAME");
                    String number = (String) object.get("USERPHONE");
                    String scheduleDate = (String) object.get("SCHEDULENEXTDATE");
                    String channel = "SAVVY";
                    String message = "Dear " + userName + " case number " + caseNumber + " is scheduled on " + scheduleDate + " please follow up";
                    data.put("channel", channel);
                    data.put("message", message);
                    data.put("number", number);
                    smsData.put(data);
                    sms.put("smsData", smsData);
                    inforLogger.LogTissInfo("smsDataObject" + smsData);
                    parent.rest.postSmsRequestBroker(parent.smsData, sms.toString());
                }
            }
        }
    }
}
