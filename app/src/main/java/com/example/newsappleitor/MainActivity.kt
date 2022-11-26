package com.example.newsappleitor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappleitor.databinding.ActivityMainBinding
import com.example.newsappleitor.databinding.ArtigoBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: Button = binding.btnClick
        button.setOnClickListener {
            setContentView(ArtigoBinding.inflate(layoutInflater).root)
        }
    }
}
