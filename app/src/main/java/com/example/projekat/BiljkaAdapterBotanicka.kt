package com.example.projekat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BiljkaAdapterBotanicka(var context: Context, var biljke: List<Biljka>): RecyclerView.Adapter<BiljkaAdapterBotanicka.BiljkaViewHolder>() {
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

    private var pretragaObavljena=false

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

    fun postaviNaTrue(){
        pretragaObavljena=true
    }
    fun postaviNaFalse(){
        pretragaObavljena=false
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
        trefleDAO.setContext(context)
        val db=BiljkaDatabase.getInstance(context)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val image=trefleDAO.getImage(biljka)
            //prvo dohvatamo id od biljke
            if(pretragaObavljena){
                holder.slikaBiljke.setImageBitmap(image)
            }else{
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
            }
            }

        }

        holder.nazivBiljke.text=biljka.naziv;
        holder.porodica.text=biljka.porodica
        try
        {
            holder.klima.text=biljka.klimatskiTipovi[0].opis
        }catch(e: Exception)
        {
            holder.klima.text=""
        }
        try
        {
            holder.zemlja.text=biljka.zemljisniTipovi[0].naziv
        }catch(e: Exception)
        {
            holder.zemlja.text=""
        }
        if(pretragaObavljena) holder.checkBox.visibility=View.GONE
        else {
            holder.checkBox.isChecked=biljka.onlineChecked
        }
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

    fun jeLiPretragaObavljena():Boolean{
        return pretragaObavljena
    }

    fun filterByCriteria(biljka: Biljka): List<Biljka>
    {
        if(pretragaObavljena) return biljke.toList()
        val sharedClimates = biljka.klimatskiTipovi
        val sharedSoils = biljka.zemljisniTipovi
        val porodica=biljka.porodica

        biljke = biljke.toList().filter { it.klimatskiTipovi.intersect(sharedClimates).isNotEmpty() &&
                it.zemljisniTipovi.intersect(sharedSoils).isNotEmpty() &&
        it.porodica==porodica}
        notifyDataSetChanged()
        return biljke.toList()
    }
}