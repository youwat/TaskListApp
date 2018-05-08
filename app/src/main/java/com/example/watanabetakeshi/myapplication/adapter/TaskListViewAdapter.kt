package com.example.watanabetakeshi.myapplication.adapter

import android.content.Context;
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.watanabetakeshi.myapplication.R

public class TaskListViewAdapter : ArrayAdapter<TaskListViewItem>
{

    private val mResource : Int
    private val mItems : MutableList<TaskListViewItem>
    private val mInflater : LayoutInflater
    private val checkOnDrawable : Drawable
    private val checkOffDrawable : Drawable

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    constructor(context : Context, resource : Int, items : MutableList<TaskListViewItem>) : super(context, resource, items) {
        mResource = resource;
        mItems = items;
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        checkOnDrawable = ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background)!!
        checkOffDrawable = ContextCompat.getDrawable(context, android.R.drawable.checkbox_off_background)!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        // 最初にViewの設定
        val view = (
                if(convertView != null) {
                    convertView
                } else {
                    mInflater.inflate(mResource, null)
                }
        )

        // １要素の情報を取得
        val item = mItems[position]
        // 各リストビューアイテムの要素に適用する
        // 高さ指定があった場合
        if(item.height != null) {
            // TODO: 未実装
            // 参照中: https://qiita.com/Tsutou/items/e0e70d6cf81db5b9d13a
        }
        //チェックボックスを設定
        val checkDrawable = if(item.checkBoxState) checkOnDrawable else checkOffDrawable
        val checkboxImage = view.findViewById<ImageView>(R.id.checkBoxImage)
        checkboxImage.setImageDrawable(checkDrawable)
        // タスク名を設定
        view.findViewById<TextView>(R.id.taskName).text = item.name
        return view
    }
}