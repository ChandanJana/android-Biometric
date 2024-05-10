plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.biomatricapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.biomatricapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        //setProperty("archivesBaseName", "Biometric-v$versionCode($versionName)")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(project.property("KEYSTORE_FILE").toString())
            storePassword = project.property("KEYSTORE_PASSWORD").toString()
            keyAlias = project.property("SIGNING_KEY_ALIAS").toString()
            keyPassword = project.property("SIGNING_KEY_PASSWORD").toString()
        }
    }

    buildTypes {
        release {
            getByName("release"){
                isMinifyEnabled = true
                signingConfig = signingConfigs.getByName(name)
            }
            //isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //signingConfig = $("android.signingConfigs.release")
        }
        getByName("debug") {
            // your configuration here
            signingConfig = signingConfigs.getByName(name)
        }

        /*getByName("release") {
            // your configuration here
signingConfig = signingConfigs.getByName(name)
        }*/

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation ("androidx.biometric:biometric:1.0.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}