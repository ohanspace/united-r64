package org.badhan.blooddonor.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public abstract class ServiceResponse {
    private static final String TAG = "ServiceResponse";

    private String operationError;
    private boolean critical;
    private HashMap<String, String> propertyErrors;

    public ServiceResponse(){
        propertyErrors = new HashMap<>();
    }

    public ServiceResponse(String operationError){
        this.operationError = operationError;
    }

    public ServiceResponse(String operationError, boolean critical){
        this.operationError = operationError;
        this.critical = critical;
    }

    public boolean succeed(){
        return (operationError == null || operationError.isEmpty())
                && (propertyErrors.size() == 0);
    }


    public String getOperationError() {
        return operationError;
    }

    public void setOperationError(String operationError) {
        this.operationError = operationError;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public void setPropertyError(String property, String error){
        propertyErrors.put(property, error);
    }

    public String getPropertyError(String property){
        return propertyErrors.get(property);
    }

    public void showErrorToast(Context context){
        if (context == null || operationError == null || operationError.isEmpty())
            return;
        try{
            Toast.makeText(context, operationError, Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Log.e(TAG, "cant create error toast", e);
        }
    }
}
