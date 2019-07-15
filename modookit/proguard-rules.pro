# proguard for tencnet
-keep class com.tencent.mm.opensdk.** { *; }

-keep class com.tencent.wxop.** { *; }

-keep class com.tencent.mm.sdk.** { *; }
#proguard for modoo
-keep interface com.we.modoo.callback.** {*;}

-keep interface com.we.modoo.share.IShare {*;}

-keep class com.we.modoo.ModooHelper {*;}

-keep class com.we.modoo.core.WXEntryActivity {*;}

-keep class com.we.modoo.core.LoginType {*;}

-keep class com.we.modoo.core.ShareType {*;}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


