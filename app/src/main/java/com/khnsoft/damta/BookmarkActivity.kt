package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.*
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.activity_bookmark.*

class BookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        btn_back.setOnClickListener {
            finish()
        }

        val lBookmark = Bookmark.getByUser(this@BookmarkActivity, User.current ?: return)

        val adapter = BookmarkRecyclerAdapter(lBookmark)
        val lm = LinearLayoutManager(this@BookmarkActivity)
        bookmark_container.layoutManager = lm
        bookmark_container.adapter = adapter
    }

    inner class BookmarkRecyclerAdapter(val lBookmark: Array<Bookmark>) :
        RecyclerView.Adapter<BookmarkRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val container = itemView.findViewById<LinearLayout>(R.id.item_container)
            val name = itemView.findViewById<TextView>(R.id.tv_name)
            val type = itemView.findViewById<TextView>(R.id.tv_type)
            val address = itemView.findViewById<TextView>(R.id.tv_address)
            val reviewCount = itemView.findViewById<TextView>(R.id.tv_review_count)
            val thumb = itemView.findViewById<CheckBox>(R.id.cb_thumb)
            val thumbCount = itemView.findViewById<TextView>(R.id.tv_thumb)
            val bookmark = itemView.findViewById<CheckBox>(R.id.cb_bookmark)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@BookmarkActivity).inflate(R.layout.item_bookmark, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lBookmark.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val area = lBookmark[position].area ?: return
            holder.name.text = area.name
            holder.type.text = when (area.type) {
                AreaType.OPENED -> getString(R.string.area_type_opened)
                AreaType.CLOSED -> getString(R.string.area_type_closed)
                AreaType.FULLYCLOSED -> getString(R.string.area_type_fullyclosed)
            }
            holder.address.text = area.address
            holder.reviewCount.text = Review.getCountByArea(this@BookmarkActivity, area).toString()
            holder.thumb.isChecked = Thumb.isThumbed(this@BookmarkActivity, User.current, area)
            holder.thumbCount.text = Thumb.getCountByArea(this@BookmarkActivity, area).toString()
            holder.bookmark.isChecked = Bookmark.isBookmarked(this@BookmarkActivity, User.current, area)
        }
    }
}