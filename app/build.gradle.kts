plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.vsanto.gameapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vsanto.gameapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "IGDB_BASE_URL", "\"https://api.igdb.com/v4/\"")
            buildConfigField("String", "IGDB_CLIENT_ID", "\"8kjgodb5ozfyj3q70rmekr9lioe59z\"")
            buildConfigField(
                "String",
                "IGDB_AUTHORIZATION_BEARER",
                "\"Bearer z4g3m810kqolfmnac3hhr5as2poa7p\""
            )
        }
        getByName("debug") {
            isDebuggable = true
            buildConfigField("String", "IGDB_BASE_URL", "\"https://api.igdb.com/v4/\"")
            buildConfigField("String", "IGDB_CLIENT_ID", "\"8kjgodb5ozfyj3q70rmekr9lioe59z\"")
            buildConfigField(
                "String",
                "IGDB_AUTHORIZATION_BEARER",
                "\"Bearer z4g3m810kqolfmnac3hhr5as2poa7p\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Room
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    //DaggerHilt
    val daggerVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    kapt("com.google.dagger:hilt-compiler:$daggerVersion")

    //NavComponent
    val navVersion = "2.7.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //UnitTesting
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.12.3")

    //UITesting
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}