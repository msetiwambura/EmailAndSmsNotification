package com.crdb;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class SendEmailNotification implements WrapperListener {
    public String server = "";
    public String port = "";
    public String requestData = "";
    public String postData = "";
    public String smsServer = "";
    public String smsPort = "";
    public String smsData = "";
    public int timeInHours;
    public int timeInMinutes;
    public int timeInSeconds;
    public long timeInterval;
    public String[] argsx;
    public RestBroker rest;
    public EmailScheduler emailScheduler;
    private Scheduler sched = null;

    public SendEmailNotification(String[] args) {
        this.argsx = args;
        for (int i = 0; i < argsx.length; i++) {
            String params = argsx[i];
            if (params.equals("-server")) {
                server = argsx[i + 1];
            } else if (params.equals("-port")) {
                port = argsx[i + 1];
            } else if (params.equals("-requestData")) {
                requestData = argsx[i + 1];
            } else if (params.equals("-postData")) {
                postData = argsx[i + 1];
            } else if (params.equals("-timeInHours")) {
                timeInHours = Integer.parseInt(argsx[i + 1]);
            } else if (params.equals("-timeInMinutes")) {
                timeInMinutes = Integer.parseInt(argsx[i + 1]);
            } else if (params.equals("-timeInSeconds")) {
                timeInSeconds = Integer.parseInt(argsx[i + 1]);
            } else if (params.equals("-timeInterval")) {
                timeInterval = Long.parseLong(argsx[i + 1]);
            } else if (params.equals("-smsServer")) {
                smsServer = argsx[i + 1];
            } else if (params.equals("-smsPort")) {
                smsPort = argsx[i + 1];
            } else if (params.equals("-smsData")) {
                smsData = argsx[i + 1];
            }
        }
        rest = new RestBroker(this);
        emailScheduler = new EmailScheduler(this);
    }

    public static void main(String args[]) {
        WrapperManager.start(new SendEmailNotification(args), args);
    }

    @Override
    public Integer start(String[] arg0) {
        try {
            sched = StdSchedulerFactory.getDefaultScheduler();
            sched.start();
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public int stop(int i) {
        try {
            sched.shutdown();
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public void controlEvent(int i) {

    }

}
