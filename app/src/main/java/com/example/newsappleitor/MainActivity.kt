package com.example.newsappleitor

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappleitor.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var db = FirebaseFirestore.getInstance()
    private lateinit var artigoRecyclerView: RecyclerView
    private val artigoMutableList: MutableList<Artigo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        artigoRecyclerView = binding.rvNoticias
        artigoRecyclerView.layoutManager = LinearLayoutManager(this)
        artigoRecyclerView.setHasFixedSize(true)

        puxarArtigos()
        atualizar()
    }

    private fun puxarArtigos() {
        db.collection("notícias").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (artigos in it.documents) {
                    val artigo = artigos.toObject(Artigo::class.java)
                    artigoMutableList.add(artigo!!)
                }

                artigoRecyclerView.adapter = MyAdapter(this@MainActivity, artigoMutableList)
            } else {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.vazio)
                    .setMessage(R.string.sem_registro)
                    .setPositiveButton("Okay") { _, _ -> }
                    .show()
            }
        }

            .addOnFailureListener {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.erro)
                    .setMessage(R.string.bd_erro)
                    .setPositiveButton("Okay") { _, _ -> }
                    .show()
            }
    }

    fun atualizar() {
        val refresh = binding.swipeRv
        refresh.setOnRefreshListener {
            artigoMutableList.clear()
            puxarArtigos()
            refresh.isRefreshing = false

            val toast = Toast.makeText(this, R.string.atualizar, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER, 0, 0)
            toast.show()
            setContentView(binding.root)
        }
    }
}
