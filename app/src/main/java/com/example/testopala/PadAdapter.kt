package com.example.testopala

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat

class PadAdapter(
    val padList: List<PadEntity>,
    val heightGridView: Int,
    mListener: selectedPadItem
) : BaseAdapter() {

    private var mContext: Context? = null
    var mArrayListView = arrayListOf<ImageView>()
    var mlistPadAdapter = mListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val pad = this.padList[position]

        mContext = parent?.context
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)

        val mViewPadItem = view.findViewById<ImageView>(R.id.id_pad)
        mViewPadItem.layoutParams.height = heightGridView / 4
        mViewPadItem.tag = pad.pad //position

        mArrayListView.add(mViewPadItem)

        customSelectorImageView(mViewPadItem, pad)

        if (position == this.padList.size - 1) {
            mlistPadAdapter.onPadClick()

        }
        return view
    }

    interface selectedPadItem {
        fun onPadClick()
    }

    private fun customSelectorImageView(ivPad: ImageView, pad: PadEntity) {

        var colorPadSelector = 0

        when (pad.color) {
            Constants.COLORSJSON.PINK -> colorPadSelector = R.color.pad_pink
            Constants.COLORSJSON.BLUE -> colorPadSelector = R.color.pad_blue
            Constants.COLORSJSON.GREEN -> colorPadSelector = R.color.pad_green
            Constants.COLORSJSON.ORANGE -> colorPadSelector = R.color.pad_orange
        }


        val layerDrawable =  ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad_selected) as (LayerDrawable)
        val selectedCustom = layerDrawable.findDrawableByLayerId(R.id.layerItem)

        (selectedCustom as GradientDrawable).setColor(ContextCompat.getColor(mContext!!, colorPadSelector))

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), selectedCustom)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_selected),
            ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad))

        ViewCompat.setBackground(ivPad, stateListDrawable)
    }

    fun seekPad(pad: Int) {
        for (img in mArrayListView) {
            if (img.tag == pad) {
                if (!img.isSelected) {
                    img.isSelected = true
                    Thread.sleep(500)
                    img.elevation = 0F
                }
                img.isSelected = false
            }
        }
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