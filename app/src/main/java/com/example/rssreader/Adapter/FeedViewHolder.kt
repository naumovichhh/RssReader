package com.example.rssreader.Adapter

import com.example.rssreader.Interface.ItemClickListener
import com.example.rssreader.Model.RSSObject
import com.example.rssreader.Activities.OneNewsActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rssreader.R
import com.bumptech.glide.Glide

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {
    var txtTitle: TextView
    var txtPubDate: TextView
    var srcImage: ImageView
    private var itemClickListener: ItemClickListener? = null

    init {
        txtTitle =
            itemView.findViewById(R.id.textTitle) as TextView
        txtPubDate = itemView.findViewById(R.id.txtPubdate) as TextView
        srcImage =
            itemView.findViewById(R.id.imageNews) as ImageView
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener (this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context) : RecyclerView.Adapter<FeedViewHolder>() {
    private val inflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubDate.text = rssObject.items[position].pubDate
        Glide.with(mContext).load(rssObject.items[position].thumbnail).into(holder.srcImage)
        holder.setItemClickListener(ItemClickListener { _, pos, isLongClick ->
            if (!isLongClick) {
                val intent = Intent(mContext, OneNewsActivity::class.java)
                intent.putExtra("TITLE", rssObject.items[pos].title)
                intent.putExtra("DATE", rssObject.items[pos].pubDate)
                intent.putExtra("CONTENT", rssObject.items[pos].content)
                intent.putExtra("AUTHOR", rssObject.items[pos].author)
                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int = rssObject.items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(
            itemView
        )
    }
}