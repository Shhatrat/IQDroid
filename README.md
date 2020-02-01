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
 - IqScreen
 - IqScreenItem
 - IqNavigation
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
