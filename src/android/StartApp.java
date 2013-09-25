package com.cordova.plugins.startapp;

import org.apache.cordova.DroidGap;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Intent;


/**
 * Launches an external application.
 *
 * @author Dmitry Medvinsky <dmedvinsky@gmail.com>
 * @license MIT/X11
 */
public class StartApp extends CordovaPlugin
{
    private CallbackContext callbackContext = null;

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action
     *          Action to perform.
     * @param args
     *          Arguments to the action.
     * @param callbackId
     *          JavaScript callback ID.
     * @return A PluginResult object with a status and message.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
    {
        try {
            this.callbackContext = callbackContext;

            if (action.equals("startApp")) {
                if (args.length() != 1) {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
                    return false;
                }
                String component = args.getString(0);
                startActivity(component);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
                return true;
            }
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
            return false;
        }
    }

    /**
     * Starts an activity.
     *
     * @param component
     *            Activity ComponentName.
     *            E.g.: com.mycompany.myapp/com.mycompany.myapp.MyActivity
     */
    void startActivity(String component) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(ComponentName.unflattenFromString(component));
        ((DroidGap) this.cordova.getActivity()).startActivity(intent);
    }
}
