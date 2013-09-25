/**
 * Cordova StartApp plugin
 * Author: Dmitry Medvinsky <dmedvinsky@gmail.com>
 * License: MIT/X11
 */
var StartApp = function() { };

StartApp.prototype.start = function(params, success, fail) {
    success = success ? success : function() {};
    fail = fail ? fail : function() {};
    var packageName = params.packageName;
    var mainActivity = params.mainActivity;
    return cordova.exec(success, fail, 'StartApp', 'startApp', [packageName, mainActivity]);
};

window.startapp = new StartApp();
