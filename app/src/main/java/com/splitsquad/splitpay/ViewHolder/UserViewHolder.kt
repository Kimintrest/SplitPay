package com.splitsquad.splitpay.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.splitsquad.splitpay.Interface.IRecyclerItemClickListener
import com.splitsquad.splitpay.R
import com.splitsquad.splitpay.R.layout.nav_header
//import res/layout/layout_user.xml

class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txt_user_email:TextView
    lateinit var iRecyclerItemClickListener:IRecyclerItemClickListener

    fun setClick(iRecyclerItemClickListener:IRecyclerItemClickListener){
        this.iRecyclerItemClickListener = iRecyclerItemClickListener

    }
    init{
        txt_user_email = itemView.findViewById<TextView>(R.id.email_textView)

        itemView.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        iRecyclerItemClickListener.onItemClickListener(p0!!, adapterPosition)

    }
}