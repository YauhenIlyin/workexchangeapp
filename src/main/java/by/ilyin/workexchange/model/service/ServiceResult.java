package by.ilyin.workexchange.model.service;

import java.util.HashMap;

public class ServiceResult {

    private HashMap<String, Object> resultHashMap = new HashMap<>();
    private boolean isResultSuccessful = true;

    public ServiceResult() {
    }

    public HashMap<String, Object> getResultHashMap() {
        return resultHashMap;
    }

    public void setResultHashMap(HashMap<String, Object> resultHashMap) {
        this.resultHashMap = resultHashMap;
    }

    public boolean isResultSuccessful() {
        return isResultSuccessful;
    }

    public void setResultSuccessful(boolean resultSuccessful) {
        isResultSuccessful = resultSuccessful;
    }
}
