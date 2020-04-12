# IQdroid

Android library for easy intergation with monkey-c devices.

Library for monkey-c: https://github.com/Shhatrat/IQDroid-garmin

# Main goals:
- wrapping garmin's library with RxJava2
- easy access to garmin's data
- creating IQ app without coding in monkey-c

# Usage
### Init
Firstly you should create `IQDroid` object. 
```
IQDroid(applicationContext, ConnectIQ.IQConnectType.WIRELESS, id)
```
There are two types of `connectionType`:
 - ConnectIQ.IQConnectType.WIRELESS - for Bluetooth connection
 - ConnectIQ.IQConnectType.TETHERED - for adb connection with simulator

You should copy `id` from your IQ app.

# Main classes
### Raw
`Raw` class wrapping main IQ Android SDK functions. You should avoid using `Raw.sendMessage()` function. This function is broken by garmin API site. More information: https://forums.garmin.com/developer/connect-iq/f/legacy-bug-reports/5144/failure_during_transfer-issue-again-now-using-comm-sample. For full communication use `IQDataManager`

### IQDataManager
IQDataManager allows us to manage data beetween Android and IQ device in easy way.
#### Features:
##### Requests
Request have `IQRequestType` type. It automatically notify IQ which data we need in Android app. 

Functions:
```
fun addType(type: IQRequestType)
fun remove(type: IQRequestType)
```
##### Other
`Other` can easly transform data between Android and IQ device. It takes 1-2s delay.
```
fun addOther(data: String)
```

### ScreenManager
`ScreenManager` is attempt to create 100% responsive IQ app without coding in monkey-c. There are 3 main concepts:
 - Screen. `Screen` is something like `View`. It can contains multiple `ScreenItem` and `Navigation` objects. Every Screen should have uniqe `id`
 - ScreenItem. It is smallest part of layout (text, line). It is draw automatically when `Screen` is appear. Every `ScreenItem` have information about position, color and some additional data connected with special type (for example circle has radius field). 
 - Navigation. Contains information about `Screen` and `KeyCode`. If proper `KeyCode` is pressed library automatically change `Screen`.
 
 ## Usage
 Firstly, you have to create `Screen` object with `Screen.Builder()`. 
```
val screen = Screen.Builder().description("data screen").build()
```
Add it into `ScreenManager`
```
val screenWithId = ScreenManager.addScreen(screen1)
``` 
Now we have `Screen` object with uniq ID.

### Navigation
This is only way to exit from screen flow.
```
ScreenManager.addExitToKey(screenWithId, ScreenManager.KEY.DOWN)
```
Adding navigation between Screen1 -> Screen2
```
ScreenManager.addScreenToKey(screenWithId1, screenWithId2, ScreenManager.KEY.UP)
```
Removing navigation 
```
ScreenManager.removeScreenToKey(screenWithId1, ScreenManager.KEY.UP)
```
### ScreenItem
There are several types of `ScreenItem`, similar to native IQ Items:
- Text
- Line
- RectangleRounded
- Rectangle
- Circle
- Eclipse
- Background
example of creating text object:
```
val textScreenItem = IqScreenItem.Text(80, 80, 16711680, 16776960, 3, "text", 1)
```
Adding `ScreenItem` to `Screen`:
```
ScreenManager.addScreenItem(screenWithId, textScreenItem)
```
Removing `ScreenItem` from `Screen`:
```
ScreenManager.removeScreenItem(screenWithId, textScreenItem)
```
### Other
Removing all `Screens`
```
ScreenManager.removeAllScreens()
```
Setting screen's data to device:
```
connectIq.iqDataManager.addIQScreens(ScreenManager.getCurrentJson())
```
### Receiver
If you want notify android app when IQ app is started you can use `BroadcastReceiver`.

Add `<action android:name="com.garmin.android.connectiq.INCOMING_MESSAGE" />` to `AndroidManifest.xml`.
```
        <receiver
                android:name=".LaunchingReceiver"
                android:exported="true">
            <intent-filter>
                <action android:name="com.garmin.android.connectiq.INCOMING_MESSAGE" />
            </intent-filter>
        </receiver>
```
