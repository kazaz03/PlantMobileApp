package com.example.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BiljkaAdapterBotanicka(var biljke: List<Biljka>): RecyclerView.Adapter<BiljkaAdapterBotanicka.BiljkaViewHolder>() {
    val tipSlike = mapOf(
        "Bosiljak (Ocimum basilicum)" to R.drawable.bosiljak,
        "Nana (Mentha spicata)" to R.drawable.nana,
        "Kamilica (Matricaria chamomilla)" to R.drawable.kamilica,
        "Ružmarin (Rosmarinus officinalis)" to R.drawable.ruzmarin,
        "Lavanda (Lavandula angustifolia)" to R.drawable.lavanda,
        "Aloe vera (Aloe Barbadensis)" to R.drawable.aloevera,
        "Đumbir (Zingiber officinale)" to R.drawable.dzumbir,
        "Hibiskus (Rosa Sinensis)" to R.drawable.hibiskus,
        "Limun (Citrus Limon)" to R.drawable.limun,
        "Bijeli luk (Allium sativum)" to R.drawable.cesnjak
    )

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(biljka: Biljka)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    private fun getImageId(name: String): Int? {
        if (tipSlike.containsKey(name)) {
            return tipSlike[name] ?: 0
        } else {
            return null
        }
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanicki, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka=biljke[position];
        val ImageId = getImageId(biljka.naziv)
        if (ImageId != null) {
            holder.slikaBiljke.setImageResource(ImageId)
        }
        holder.nazivBiljke.text=biljka.naziv;
        holder.porodica.text=biljka.porodica
        holder.klima.text=biljka.klimatskiTipovi[0].opis
        holder.zemlja.text=biljka.zemljisniTipovi[0].naziv
    }

    override fun getItemCount(): Int {
        return biljke.size;
    }

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem);
        val klima: TextView=itemView.findViewById(R.id.klimatskiTipItem);
        val zemlja: TextView=itemView.findViewById(R.id.zemljisniTipItem);
        val porodica: TextView=itemView.findViewById(R.id.porodicaItem)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                    val clickedBiljka = biljke[position]
                    itemClickListener!!.onItemClick(clickedBiljka)
                }
            }
        }
    }

    fun filterByCriteria(biljka: Biljka): List<Biljka>
    {
        val sharedClimates = biljka.klimatskiTipovi
        val sharedSoils = biljka.zemljisniTipovi

        biljke = biljke.toList().filter { it.klimatskiTipovi.intersect(sharedClimates).isNotEmpty() && it.zemljisniTipovi.intersect(sharedSoils).isNotEmpty() }
        notifyDataSetChanged()
        return biljke.toList()
    }
}