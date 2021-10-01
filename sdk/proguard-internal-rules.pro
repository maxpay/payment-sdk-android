# proguard rules related to project, kotlin
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,EnclosingMethod

-keep class kotlin.Metadata { *; }
-dontwarn kotlin.reflect.jvm.internal.**
-keep class kotlin.reflect.** { *; }


-keepclassmembers public class com.maxpay.sdk.payment.** {
    public synthetic <methods>;
}

#-keep class com.maxpay.sdk.common.**{*;}
-keep class com.maxpay.sdk.payment.SdkFacadeImpl {
    *;
}
-keep interface com.maxpay.sdk.payment.SDKFacade {
    *;
}

# For stack traces
-keepattributes SourceFile, LineNumberTable

# keep data class
-keepclasseswithmembers class com.maxpay.sdk.payment.** {
    public ** component1();
    <fields>;
}


# For enumeration classes
-keep public enum com.maxpay.sdk.payment.**{
    *;
}

# https://www.guardsquare.com/en/products/proguard/manual/examples#enumerations
-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public *;
}