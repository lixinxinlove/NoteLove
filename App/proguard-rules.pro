# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontwarn
-dontoptimize
-dontpreverify



# use Annotation
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

#native
-keepclasseswithmembernames class *{
	native <methods>;
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


# Keep the support library
-keep class android.support.** { *; }
-keep interface android.support.** { *; }


# 抑制警告
#-ignorewarnings

#Gson
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-dontwarn com.google.gson.**


# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
# support-design end


-keep class com.eventmosh.evente.entity.**{*;}


-keep public class * extends android.app.Activity                       # 保持哪些类不被混淆
-keep public class * extends android.app.Application                    # 保持哪些类不被混淆
-keep public class * extends android.app.Service                        # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver          # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider            # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper       # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference              # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService      # 保持哪些类不被混淆

# 保留R下面的资源
-keep class **.R$* {*;}
#=========================保持内部类不被混淆==============================
#-keepclassmembers class com.eventmosh.evente.activity.EventSelectCourierCompanyActivity$Company {
#   public *;
#}

# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}


#=====================================================
-dontwarn okhttp3.**
-dontwarn okio.**

-keep class org.xmlpull.v1.**{*; }
-dontwarn org.xmlpull.v1.**
-keep public class * extends org.xmlpull.v1.XmlPullParser
-keep public class * extends org.xmlpull.v1.XmlSerializer
#Warning:library class android.content.Intent depends on program class org.xmlpull.v1.XmlPullParser
#Warning:library class android.content.IntentFilter depends on program class org.xmlpull.v1.XmlSerializer

-keep class com.gprinter.**{*;}
-keep class taobe.tec.jcc.**{*;}
-keep class org.**{*;}

#===========================BaseRecyclerViewAdapterHelper=================================
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


#==========================================glide=================================================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

#----------- rxjava rxandroid----------------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent
