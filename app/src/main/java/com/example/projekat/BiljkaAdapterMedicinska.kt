package com.example.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BiljkaAdapterMedicinska(var biljke: List<Biljka>): RecyclerView.Adapter<BiljkaAdapterMedicinska.BiljkaViewHolder>() {

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

    //interfejs za klik

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

    fun updateBiljke(biljka: List<Biljka>) {
        biljke = biljka.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicinski, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka=biljke[position];
        val ImageId = getImageId(biljka.naziv)
        if (ImageId != null) {
            holder.slikaBiljke.setImageResource(ImageId)
        }
        holder.nazivBiljke.text=biljka.naziv;
        try
        {
            holder.korist1.text=biljka.medicinskeKoristi[0].opis;
        }catch(e:Exception)
        {
            holder.korist1.text="";
        }
        try
        {
            holder.korist2.text=biljka.medicinskeKoristi[1].opis;
        }catch(e:Exception)
        {
            holder.korist2.text="";
        }
        try
        {
            holder.korist3.text=biljka.medicinskeKoristi[2].opis;
        }catch(e:Exception)
        {
            holder.korist3.text="";
        }
        try {
            holder.upozorenje.text=biljka.medicinskoUpozorenje;}
        catch(e:Exception)
        {
            holder.upozorenje.text=""
        }
    }

    override fun getItemCount(): Int {
        return biljke.size;
    }

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem);
        val korist1: TextView =itemView.findViewById(R.id.korist1Item);
        val korist2: TextView =itemView.findViewById(R.id.korist2Item);
        val korist3: TextView =itemView.findViewById(R.id.korist3Item);
        val upozorenje: TextView =itemView.findViewById(R.id.upozorenjeItem);

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

    //filtriranje
    fun filterByCriteria(biljka: Biljka): List<Biljka>
    {
        biljke = biljke.toList().filter { it.medicinskeKoristi.any { korist -> biljka.medicinskeKoristi.contains(korist) } }
        notifyDataSetChanged()
        return biljke.toList()
    }
}