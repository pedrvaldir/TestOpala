package com.example.testopala

import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.pad_entry.view.*

class PadAdapter(val padList: List<PadEntity>, val heightGridView: Int) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val pad = this.padList[position]

        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)

        val ivPad = view.findViewById<ImageView>(R.id.id_pad)
        ivPad.layoutParams.height = heightGridView / 4


        return view
    }

    override fun getItem(position: Int): Any {
        return padList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return padList.size
    }
}