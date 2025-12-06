package com.example.fieldsyncpro.ui

import android.app.Application
import com.google.firebase.FirebaseApp

class FieldSyncApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}