# Ads-Multi-Sdk
A library for displaying ads from multiple ad networks

Implementation build.gradle (Module: app)
<pre>
implementation 'com.github.kada-dev:sdk-multi-ads:2.+'
</pre>
<pre>
implementation 'com.github.kada-dev:sdk-multi-ads:2.3.5'
</pre>
Open settings.gradle (Project Settings) and update the dependencyResolutionManagement
<pre>
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        maven { url 'https://unity3ddist.jfrog.io/artifactory/unity-mediation-mvn-prod-local/' }
        maven { url 'https://maven.wortise.com/artifactory/public' }
        maven { url 'https://android-sdk.is.com/' }
        maven { url 'https://artifact.bytedance.com/repository/pangle' }
        maven { url 'https://cboost.jfrog.io/artifactory/chartboost-ads/' }
        maven { url 'https://maven.ogury.co' }
    }
}
</pre>
