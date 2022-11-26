package com.example.newsappleitor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val notícias = db.collection("notícias")
        .get()
        .addOnSuccessListener { res ->
            for (document in res) {
                Log.d("NoticiaSucc", "${document}")
            }
        }

        .addOnFailureListener { e ->
            Log.d("NoticiaFail", "NOT FOUND")

        }
}