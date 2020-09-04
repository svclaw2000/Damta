package com.khnsoft.damta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.khnsoft.damta.data.Area
import com.khnsoft.damta.data.Review
import com.khnsoft.damta.data.User
import kotlinx.android.synthetic.main.activity_new_review.*
import kotlinx.android.synthetic.main.activity_new_review.btn_next
import kotlinx.android.synthetic.main.frag_new_review1.*
import kotlinx.android.synthetic.main.frag_new_review2.*

class NewReviewActivity : AppCompatActivity() {
    var curFrag = 1
    val frag1 = NewReview1Fragment.getInstance()
    val frag2 = NewReview2Fragment.getInstance()

    companion object {
        val curReview = Review()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        val area = Area.getById(this@NewReviewActivity, intent.getIntExtra(Area.EXTRA_AREA_ID, -1))
        if (area == null) {
            finish()
            return
        }

        curReview.apply {
            this.area = area
            this.user = User.current
        }

        btn_back.setOnClickListener {
            when (curFrag) {
                1 -> {
                    finish()
                }
                2 -> {
                    callPage(curFrag-1)
                }
            }
        }

        btn_next.setOnClickListener {
            when (curFrag) {
                1 -> {
                    if (et_review.text.toString().isBlank()) {
                        Toast.makeText(this@NewReviewActivity, getString(R.string.warning_empty_field), Toast.LENGTH_LONG).show()
                    } else {
                        curReview.review = et_review.text.toString()
                    }
                }
                2 -> {
                    if (!cb_density_1.isChecked) {
                        Toast.makeText(this@NewReviewActivity, getString(R.string.warning_empty_field), Toast.LENGTH_LONG).show()
                    } else {
                        curReview.density = frag2.density
                        if (curReview.add(this@NewReviewActivity)) {
                            finish()
                        }
                    }
                }
            }
        }

        callPage(1)
    }

    private fun callPage(idx: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        when (idx) {
            1 -> {
                btn_next.text = getString(R.string.next)
                btn_next.setTextColor(getColor(R.color.dark))
                transaction.replace(R.id.frag_container, NewReview1Fragment.getInstance())
                transaction.commit()
            }
            2 -> {
                btn_next.text = getString(R.string.finish)
                btn_next.setTextColor(getColor(R.color.highlight))
                transaction.replace(R.id.frag_container, NewReview2Fragment.getInstance())
                transaction.commit()
            }
        }

        curFrag = idx
    }
}