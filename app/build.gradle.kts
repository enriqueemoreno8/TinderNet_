plugins {
    id("com.android.application")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tindernet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tindernet"
        minSdk = 34
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1") // Actualizado a la versión 1.6.1
    implementation("com.google.android.material:material:1.11.0") // Actualizado a la versión 1.11.0
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Actualizado a la versión 2.1.4
    implementation("com.google.ar.sceneform:filament-android:1.17.1") // Actualizado a la versión 1.17.1
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.0") // Actualizado a la versión 3.0.0
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0") // Actualizado a la versión 1.1.0
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") // Actualizado a la versión 2.7.0
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Actualizado a la versión 2.7.0
    implementation("androidx.navigation:navigation-ui:2.7.7") // Actualizado a la versión 2.7.7
    implementation("androidx.navigation:navigation-fragment:2.7.7") // Actualizado a la versión 2.7.7

    testImplementation("junit:junit:4.13.2") // Actualizado a la versión 4.13.2
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Actualizado a la versión 1.1.5
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Actualizado a la versión 3.5.1
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Actualizado a la versión 33.0.0
    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth:21.0.1") // Actualizado a la versión 21.0.1
    implementation("com.google.firebase:firebase-analytics") // Requiere especificar la versión
    //Dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:19.2.0") // Actualizado a la versión 19.2.0

    //Pagina swipeo depencdencias
    implementation ("com.github.Yalantis:Koloda-Android:v0.0.2-alpha"){ // Requiere revisión manual para actualizaciones
        exclude(group = "androidx.core", module = "core")
    }
    implementation ("com.github.bumptech.glide:glide:4.12.0") // Actualizado a la versión 4.12.0
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0") // Actualizado a la versión 4.12.0
}