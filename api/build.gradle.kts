plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(Kotlin.stdLib)

    // network and serialization
    implementation(Libraries.moshi)
    implementation(Libraries.okhttp_logging)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofit_moshi)
}