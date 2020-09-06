package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.Area
import com.khnsoft.damta.data.Review
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.activity_more_review.*

class MoreReviewActivity : AppCompatActivity() {
    var area : Area? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_review)

        btn_back.setOnClickListener {
            finish()
        }

        area = Area.getById(this@MoreReviewActivity, intent.getIntExtra(Area.EXTRA_AREA_ID, -1))
        val area = area
        if (area == null) {
            finish()
            return
        }

        btn_write_review.setOnClickListener {
            val intent = Intent(this@MoreReviewActivity, NewReviewActivity::class.java)
            intent.putExtra(Area.EXTRA_AREA_ID, area.id)
            startActivity(intent)
        }
    }

    private fun refreshReview() {
        val lReview = Review.getByArea(this@MoreReviewActivity, area ?: return)

        val adapter = ReviewRecyclerAdapter(lReview)
        val lm = LinearLayoutManager(this@MoreReviewActivity)
        review_container.layoutManager = lm
        review_container.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshReview()
    }

    inner class ReviewRecyclerAdapter(val lReview: Array<Review>) :
            RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            val review = itemView.findViewById<TextView>(R.id.tv_review)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@MoreReviewActivity).inflate(R.layout.item_review, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lReview.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val review = lReview[position]
            holder.nickname.text = "${review.user?.nickname}(${review.user?.username})"
            holder.date.text = SDF.dateDot.format(review.createdDate)
            holder.review.text = review.review
        }
    }
}