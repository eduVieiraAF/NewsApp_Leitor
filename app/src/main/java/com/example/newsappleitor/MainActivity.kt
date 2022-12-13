package com.example.newsappleitor

import android.app.Dialog
import android.os.Bundle
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
    private var mProgressDialog: Dialog? = null

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

                customToastOff()

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

    private fun atualizar() {
        val refresh = binding.swipeRv
        refresh.setOnRefreshListener {
            artigoMutableList.clear()
            puxarArtigos()
            refresh.isRefreshing = false

            customToastOn()
            setContentView(binding.root)
        }
    }

    private fun customToastOn() {
        mProgressDialog = Dialog(this)
        mProgressDialog.let {
            it?.setContentView(R.layout.dialog_custom_progress)
            it?.show()
        }
    }

    private fun customToastOff() {
        mProgressDialog?.dismiss()
    }
}
