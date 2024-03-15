package com.example.cryptolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.models.Cryptocurrency

class MyAdapter(private var cryptoData : ArrayList<Cryptocurrency>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val TAG = "CL_MyAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        Log.i(TAG, "onCreateViewHolder: ")
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_view,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount: ")
        return cryptoData.size
    }

    fun filterList(myFilterList: ArrayList<Cryptocurrency>){
        cryptoData = myFilterList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder: ")
        holder.coinTitle.text = cryptoData[position].name
        holder.coinSymbol.text = cryptoData[position].symbol
        if(cryptoData[position].type == "coin"){
            holder.image.setImageResource(R.drawable.bitcoin)
        }else{
            holder.image.setImageResource(R.drawable.token)
        }
        if(!cryptoData[position].is_active){
            holder.image.setImageResource(R.drawable.inactive_coin)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var coinTitle = itemView.findViewById<TextView>(R.id.coin_title)
        var coinSymbol = itemView.findViewById<TextView>(R.id.coin_symbol)
        var image = itemView.findViewById<ImageView>(R.id.coin_image)
    }
}

