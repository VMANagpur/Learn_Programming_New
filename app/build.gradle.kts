plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.learnprogramming"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.learnprogramming"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.airbnb.android:lottie:6.0.0")//lottie animation
    //noinspection GradleCompatible
//    implementation ("com.android.support:design:29.0.0")
    //noinspection GradleCompatible
    implementation ("com.google.android.material:material:1.1.0-alpha10")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    //For serialising JSONP add converter-scalars
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
//An Adapter for adapting RxJava 2.x types.
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")


}