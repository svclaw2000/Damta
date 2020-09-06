package com.khnsoft.damta

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.*
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    var checkboxArray = arrayOf<CheckBox>()
    var area : Area? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mHandler = Handler()
        mHandler.postDelayed({
            view_banner.visibility = View.GONE
        }, 3000)

        checkboxArray = arrayOf(cb_density_1, cb_density_2, cb_density_3, cb_density_4, cb_density_5)

        area = Area.getById(this@DetailActivity, intent.getIntExtra(Area.EXTRA_AREA_ID, -1))
        val area = area
        if (area == null) {
            finish()
            return
        }

        btn_back.setOnClickListener {
            finish()
        }

        tv_type.text = when (area.type) {
            AreaType.OPENED -> getString(R.string.area_type_opened)
            AreaType.CLOSED -> getString(R.string.area_type_closed)
            AreaType.FULLYCLOSED -> getString(R.string.area_type_fullyclosed)
        }

        tv_name.text = area.name
        cb_bookmark.isChecked = Bookmark.isBookmarked(this@DetailActivity, User.current, area)
        tv_address.text = area.address

        cb_thumb.isChecked = Thumb.isThumbed(this@DetailActivity, User.current, area)
        tv_thumb.text = Thumb.getCountByArea(this@DetailActivity, area).toString()
        checkDensity(area.density)

        cb_bookmark.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    Bookmark.add(this@DetailActivity, User.current ?: return@setOnCheckedChangeListener, area)
                }
                false -> {
                    Bookmark.remove(this@DetailActivity, User.current ?: return@setOnCheckedChangeListener, area)
                }
            }
        }

        cb_thumb.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    Thumb.add(this@DetailActivity, User.current ?: return@setOnCheckedChangeListener, area)

                }
                false -> {
                    Thumb.remove(this@DetailActivity, User.current ?: return@setOnCheckedChangeListener, area)
                }
            }
            tv_thumb.text = Thumb.getCountByArea(this@DetailActivity, area).toString()
        }

        btn_navigate.setOnClickListener {
            try {
                val uri = Uri.parse("kakaomap://route?ep=${area.x},${area.y}&by=FOOT")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(uri)
                startActivity(intent)
            } catch (e: Exception) {
                val uri = Uri.parse("https://map.kakao.com/link/to/${area.address},${area.x},${area.y}")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(uri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }

        if (area.ashtray) {
            cb_ashtray.isChecked = true
            tv_ashtray.setTextColor(R.color.highlight)
        }
        if (area.vent) {
            cb_vent.isChecked = true
            tv_vent.setTextColor(R.color.highlight)
        }
        if (area.bench) {
            cb_bench.isChecked = true
            tv_bench.setTextColor(R.color.highlight)
        }
        if (area.machine) {
            cb_machine.isChecked = true
            tv_machine.setTextColor(R.color.highlight)
        }

        val originalImage = Image.getByArea(this@DetailActivity, area)
        val lImage = if (originalImage.size > 2) {
            arrayOf(originalImage[0], originalImage[1], originalImage[2])
        } else {
            originalImage
        }

        val imageAdapter = ImageRecyclerAdapter(lImage, area)
        val imageLm = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        photo_container.layoutManager = imageLm
        photo_container.adapter = imageAdapter

        btn_write_review.setOnClickListener {
            val intent = Intent(this@DetailActivity, NewReviewActivity::class.java)
            intent.putExtra(Area.EXTRA_AREA_ID, area.id)
            startActivity(intent)
        }

        btn_more_review.setOnClickListener {
            val intent = Intent(this@DetailActivity, MoreReviewActivity::class.java)
            intent.putExtra(Area.EXTRA_AREA_ID, area.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshReview()
    }

    private fun refreshReview() {
        val originalReview = Review.getByArea(this@DetailActivity, area ?: return)
        val lReview = if (originalReview.size > 1) {
            arrayOf(originalReview[0], originalReview[1])
        } else {
            originalReview
        }

        val reviewAdapter = ReviewRecyclerAdapter(lReview)
        val reviewLm = LinearLayoutManager(this@DetailActivity)
        review_container.layoutManager = reviewLm
        review_container.adapter = reviewAdapter
    }

    private fun checkDensity(n: Double) {
        for (i in 0..4) {
            checkboxArray[i].isChecked = i < n
        }
    }

    inner class ReviewRecyclerAdapter(val lReview: Array<Review>) :
            RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            val review = itemView.findViewById<TextView>(R.id.tv_review)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@DetailActivity).inflate(R.layout.item_review, parent, false)
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

    inner class ImageRecyclerAdapter(val lImage: Array<Image>, val area: Area) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val TYPE_IMAGE = 0
        val TYPE_MORE = 1

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)

            fun bind(position: Int) {
                val image = lImage[position]
                iv_image.setImageBitmap(image.image)
            }
        }

        inner class MoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val container = itemView.findViewById<LinearLayout>(R.id.btn_more_image)

            fun bind() {
                container.setOnClickListener {
                    val intent = Intent(this@DetailActivity, MorePhotoActivity::class.java)
                    intent.putExtra(Area.EXTRA_AREA_ID, area.id)
                    startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == TYPE_IMAGE) {
                val view = LayoutInflater.from(this@DetailActivity).inflate(R.layout.item_image, parent, false)
                return ImageViewHolder(view)
            } else {
                val view = LayoutInflater.from(this@DetailActivity).inflate(R.layout.item_more_image, parent, false)
                return MoreViewHolder(view)
            }
        }

        override fun getItemCount(): Int {
            return lImage.size + 1
        }

        override fun getItemViewType(position: Int): Int {
            return if (position != lImage.size) TYPE_IMAGE else TYPE_MORE
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (getItemViewType(position) == TYPE_IMAGE) {
                (holder as ImageViewHolder).bind(position)
            } else {
                (holder as MoreViewHolder).bind()
            }
        }
    }
}