package com.example.projekat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.projekat.TrefleDAO
import com.example.projekat.NetworkUtils

class BiljkaAdapterMedicinska(var context: Context,var biljke: List<Biljka>): RecyclerView.Adapter<BiljkaAdapterMedicinska.BiljkaViewHolder>() {

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
    private var trefleDAO=TrefleDAO()

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
        trefleDAO.setContext(context)
        val biljka=biljke[position];
        val db=BiljkaDatabase.getInstance(context)
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch{
            val image=trefleDAO.getImage(biljka)
            //prvo dohvatamo id od biljke
            val bitmapRez=
                biljka.id?.let { db.biljkaDAO().getImageByBiljkaId(it) }//uzet sliku ako ima iz biljkabitmap
            //ako nema neta i bitmapRez je vratio sliku iz biljkabitmap
            if(!NetworkUtils.isNetworkAvailable(context) && bitmapRez!=null)
            {
                holder.slikaBiljke.setImageBitmap(bitmapRez.bitmap)
            }else if(!NetworkUtils.isNetworkAvailable(context) && bitmapRez==null){
                //ako nema neta i nema u bazi spasene slike, postavit ce default
                holder.slikaBiljke.setImageBitmap(image)
            }else if(NetworkUtils.isNetworkAvailable(context) && bitmapRez==null && biljka.id!=null){
                //ako ima neta i bitmaprez je vratio nista jer nema u bazi
                db.biljkaDAO().addImage(biljka.id,image)
                holder.slikaBiljke.setImageBitmap(image)
            }else if (bitmapRez!=null){
                holder.slikaBiljke.setImageBitmap(bitmapRez.bitmap)
                Log.d("dosao",biljka.naziv.toString())
            }
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
        holder.checkBox.isChecked=biljka.onlineChecked
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
        val checkBox: CheckBox =itemView.findViewById(R.id.checkBox)

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