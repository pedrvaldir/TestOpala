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
    var arrayListView = arrayListOf<ImageView>()
    var mlistPadAdapter = mListener

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val pad = this.padList[position]

        mContext = parent?.context
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)


        val mViewPadItem = view.findViewById<ImageView>(R.id.id_pad)
        mViewPadItem.layoutParams.height = heightGridView / 4
        mViewPadItem.tag = pad.pad //position

        arrayListView.add(mViewPadItem)

        view.setOnClickListener {
            view.elevation = 10F
            startAnimationSelector(mViewPadItem, pad, view)
        }



        customSelectorImageView(mViewPadItem, pad)

        if (position==this.padList.size-1){
            mlistPadAdapter.onPadClick()

        }
        return view
    }

    interface selectedPadItem{
        fun onPadClick(){

        }
    }

    private fun startAnimationSelector(ivPad: ImageView, pad: PadEntity,  view: View) {
        Thread(Runnable {
            if (!ivPad.isSelected) {
                ivPad.isSelected = true
                Thread.sleep(500)
                view.elevation = 0F
            }

            ivPad.isSelected = false

        }).start()
    }

    private fun customSelectorImageView(ivPad: ImageView, pad: PadEntity) {

       var colorPadSelector: Int = R.color.pad_green

        when(pad.color){
            "pink" -> colorPadSelector = R.color.pad_pink
            "blue" -> colorPadSelector = R.color.pad_blue
            "green" -> colorPadSelector = R.color.pad_green
            "orange" -> colorPadSelector = R.color.pad_green
        }

      val layerDrawable =  ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad_selected) as (LayerDrawable)
        val selectedCustom = layerDrawable.findDrawableByLayerId(R.id.layerItem)
        (selectedCustom as GradientDrawable).setColor(getColorWithAlpha(ContextCompat.getColor(mContext!!, colorPadSelector), 0.5F))


        val layerDrawable2 =  ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad_selected) as (LayerDrawable)
        val selectedCustom2 = layerDrawable2.findDrawableByLayerId(R.id.layerItem2)
        (selectedCustom2 as GradientDrawable).setColor(getColorWithAlpha(ContextCompat.getColor(mContext!!, colorPadSelector), 0.2F))

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), layerDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_selected),
            ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad))

        ViewCompat.setBackground(ivPad, stateListDrawable)
    }

    private fun getColorWithAlpha(color: Int, ratio: Float): Int {
        return Color.argb(Math.round(Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color))
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

    fun seekPad(pad: Int) {
        for (img in arrayListView){
            if (img.tag==pad){
                img.isSelected = true
            }
        }
    }
}