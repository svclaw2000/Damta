package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.Bookmark
import com.khnsoft.damta.data.Review
import com.khnsoft.damta.data.Thumb
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        btn_back.setOnClickListener {
            finish()
        }

        val lReview = Review.getByUser(this@ReviewActivity, User.current ?: return)

        val adapter = ReviewRecyclerAdapter(lReview)
        val lm = LinearLayoutManager(this@ReviewActivity)
        review_container.layoutManager = lm
        review_container.adapter = adapter
    }

    inner class ReviewRecyclerAdapter(val lReview: Array<Review>) :
        RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.tv_name)
            val type = itemView.findViewById<TextView>(R.id.tv_type)
            val address = itemView.findViewById<TextView>(R.id.tv_address)
            val reviewCount = itemView.findViewById<TextView>(R.id.tv_review_count)
            val thumb = itemView.findViewById<CheckBox>(R.id.cb_thumb)
            val thumbCount = itemView.findViewById<TextView>(R.id.tv_thumb)
            val bookmark = itemView.findViewById<CheckBox>(R.id.cb_bookmark)

            val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            val review = itemView.findViewById<TextView>(R.id.tv_review)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@ReviewActivity).inflate(R.layout.item_my_review, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lReview.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val review = lReview[position]

            val area = review.area ?: return
            holder.name.text = area.name
            holder.type.text = when (area.type) {
                AreaType.OPENED -> getString(R.string.area_type_opened)
                AreaType.CLOSED -> getString(R.string.area_type_closed)
                AreaType.FULLYCLOSED -> getString(R.string.area_type_fullyclosed)
            }

            holder.address.text = area.address
            holder.reviewCount.text = Review.getCountByArea(this@ReviewActivity, area).toString()
            holder.thumb.isChecked = Thumb.isThumbed(this@ReviewActivity, User.current, area)
            holder.thumbCount.text = Thumb.getCountByArea(this@ReviewActivity, area).toString()
            holder.bookmark.isChecked = Bookmark.isBookmarked(this@ReviewActivity, User.current, area)

            holder.nickname.text = "${review.user?.nickname}(${review.user?.username})"
            holder.date.text = SDF.dateDot.format(review.createdDate)
            holder.review.text = review.review
        }
    }
}