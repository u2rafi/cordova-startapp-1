cordova-startapp
================

Cordova 3.X.X Plugin for Android that allows you open an external app, if this is installed and open the market whether it's not.

```js
window.startapp.start(
  {
    packageName: 'your.package.here',
    mainActivity: 'your.package.here.MainActivity'
  },
  successCallback,
  failureCallback
);
```
