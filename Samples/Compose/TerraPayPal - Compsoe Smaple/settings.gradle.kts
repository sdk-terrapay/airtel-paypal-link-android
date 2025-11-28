pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://ext-gitlab.terrapay.com/api/v4/projects/149/packages/maven")
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "TerraPayPalXmlSdkDemo"
include(":app")
 