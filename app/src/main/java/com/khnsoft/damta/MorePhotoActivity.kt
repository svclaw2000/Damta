package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.Area
import com.khnsoft.damta.data.Image
import kotlinx.android.synthetic.main.activity_more_photo.*

class MorePhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_photo)

        btn_back.setOnClickListener {
            finish()
        }

        val area = Area.getById(this@MorePhotoActivity, intent.getIntExtra(Area.EXTRA_AREA_ID, -1))
        if (area == null) {
            finish()
            return
        }

        val lImage = Image.getByArea(this@MorePhotoActivity, area)

        val adapter = ImageRecyclerAdapter(lImage)
        val lm = GridLayoutManager(this@MorePhotoActivity, 3)
        photo_container.layoutManager = lm
        photo_container.adapter = adapter
    }

    inner class ImageRecyclerAdapter(val lImage: Array<Image>) :
        RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@MorePhotoActivity).inflate(R.layout.item_image_grid, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lImage.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val image = lImage[position]
            holder.iv_image.setImageBitmap(image.image)
        }
    }
}