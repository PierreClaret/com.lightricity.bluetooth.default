# com.Lightricity.bluetooth.default.
Old Version to decode a specific format

### The specific format:
https://github.com/PierreClaret/com.lightricity.bluetooth.default/blob/main/Data%20format.xlsx

# Usage example

If you use the file Locally it is important to place them in the same folder as your ap project. If you have any doubt follow this tuto https://lightricity.sharepoint.com/:w:/r/sites/LightricityStudentSite/_layouts/15/Doc.aspx?sourcedoc=%7BE1DAECE2-56F4-4CF7-9CEA-53C4E6D1879A%7D&file=Android%20studio%20Installation.docx&action=default&mobileredirect=true


Add the JitPack repository to your projects build.gradle file
```gradle
allprojects {
    repositories {
        maven { url "https://www.jitpack.io" }
        ...
    }
}
```

Add the library and its interface to your project by adding following as dependencies in app/build.gradle
```gradle
dependencies {
    implementation project(':bluetooth_library')
    implementation project(':default_bluetooth_library')
    ...
}
```

Define the project path in the settings.gradle file
```
include ':default_bluetooth_library', ':MPChartLib',':bluetooth_library'
project(':default_bluetooth_library').projectDir=new File('../com.lightricity.bluetooth.default/default_bluetooth_library')
project(':bluetooth_library').projectDir=new File('../com.lightricity.bluetooth/bluetooth_library')
include ':app'
}
```

Add bluetooth and location permission in AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

