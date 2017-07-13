# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Google\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}






#系统配置
-dontwarn android.os.**
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keepclasseswithmembernames class * {
    native <methods>;
}

#阿里配置
#注释调，否则会报错
#-libraryjars libs/alipaySingle-20160825.jar
-dontwarn com.alipay.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

#单独设置
-keep class com.quhuanbei.quhuanbei.ali.**{*;}