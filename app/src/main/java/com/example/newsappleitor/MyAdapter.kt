package com.example.newsappleitor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappleitor.databinding.ItemBinding

class MyAdapter(
    private val context: Context,
    private val listaArtigos: MutableList<Artigo>
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val lista = ItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(lista)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val atual = listaArtigos[position]

        holder.autor.text = atual.autor
        holder.data.text = atual.data
        holder.notícia.text = atual.notícia
        holder.título.text = atual.título
    }

    override fun getItemCount() = listaArtigos.size

    class MyViewHolder(itemView: ItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val autor = itemView.tvItemAutor
        val data = itemView.tvItemData
        val notícia = itemView.tvItemNoticia
        val título = itemView.tvItemTiulo
    }
}