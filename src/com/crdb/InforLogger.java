package com.crdb;
import org.apache.log4j.Logger;
public class InforLogger {
    final static Logger logger = Logger.getLogger(InforLogger.class);
    public void LogTissInfo(Object data){
        if(logger.isInfoEnabled()){
            logger.info("INFO " + data);
        }
    }
    public void LogTissError(Object data){
        if(logger.isDebugEnabled()){
            logger.debug("ERROR " + data);
        }
    }
}
