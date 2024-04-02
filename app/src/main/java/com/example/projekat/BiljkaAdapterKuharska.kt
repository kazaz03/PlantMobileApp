package com.example.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BiljkaAdapterKuharska(var biljke: List<Biljka>): RecyclerView.Adapter<BiljkaAdapterKuharska.BiljkaViewHolder>()  {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kuharski, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka=biljke[position];
        val ImageId = getImageId(biljka.naziv)
        if (ImageId != null) {
            holder.slikaBiljke.setImageResource(ImageId)
        }
        holder.nazivBiljke.text=biljka.naziv;
        holder.profilokusa.text=biljka.profilOkusa.opis;
        try
        {
            holder.jelo1.text=biljka.jela[0]
        }catch(e:Exception)
        {
            holder.jelo1.text=""
        }
        try
        {
            holder.jelo2.text=biljka.jela[1]
        }catch(e:Exception)
        {
            holder.jelo2.text=""
        }
        try
        {
            holder.jelo3.text=biljka.jela[2]
        }catch(e:Exception)
        {
            holder.jelo3.text=""
        }
    }

    override fun getItemCount(): Int {
        return biljke.size;
    }

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem);
        val profilokusa:TextView=itemView.findViewById(R.id.profilOkusaItem)
        val jelo1:TextView=itemView.findViewById(R.id.jelo1Item)
        val jelo2:TextView=itemView.findViewById(R.id.jelo2Item)
        val jelo3:TextView=itemView.findViewById(R.id.jelo3Item)

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
        val tasteProfile = biljka.profilOkusa
        val sharedDishes = biljka.jela

        biljke = biljke.toList().filter { it.profilOkusa == tasteProfile || it.jela.intersect(sharedDishes).isNotEmpty() }
        notifyDataSetChanged()
        return biljke.toList()
    }
}