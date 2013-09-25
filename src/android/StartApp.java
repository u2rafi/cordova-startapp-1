package com.cordova.plugins.startapp;

import org.apache.cordova.DroidGap;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


/**
 * Launches an external application.
 *
 * @author Dmitry Medvinsky <dmedvinsky@gmail.com>
 * @license MIT/X11
 */
public class StartApp extends CordovaPlugin
{
    private String packageName = null;
    private String mainActivity = null;
    
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
            if (action.equals("startApp")) {
                if (args.length() != 2) {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
                    return false;
                }
                packageName = args.getString(0);
                mainActivity = args.getString(1);
                
                startActivity(packageName+"/"+mainActivity);
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
        try{
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setComponent(ComponentName.unflattenFromString(component));
            ((DroidGap) this.cordova.getActivity()).startActivity(intent);
        }
        catch(ActivityNotFoundException ex){
            /**
             * If not installed, open market
             */
            String[] packageNameSplt = component.split("/");
            String packageName = packageNameSplt[0];

            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
            marketIntent.setData(Uri.parse("market://details?id="+packageName));
            ((DroidGap) this.cordova.getActivity()).startActivity(marketIntent);
        }
    }
}
