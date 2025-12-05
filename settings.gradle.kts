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
    }
}

rootProject.name = "FieldSync Pro"
include(":app")
include(":core")
include(":features")
include(":core:core-common")
include(":core:core-ui")
include(":core:core-database")
include(":core:core-network")
include(":core:core-sync")
include(":core:core-storage")
include(":features:auth")
include(":features:dailyjobs")
include(":features:checklist")
include(":features:assetdetails")
include(":features:photoupload")
include(":features:syncmanager")
include(":features:jobdetails")
