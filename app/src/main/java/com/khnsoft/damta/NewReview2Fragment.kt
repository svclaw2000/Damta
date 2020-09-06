package com.khnsoft.damta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.frag_new_review2.*
import kotlinx.android.synthetic.main.frag_register_area5.*
import kotlinx.android.synthetic.main.frag_register_area5.cb_density_1
import kotlinx.android.synthetic.main.frag_register_area5.cb_density_2
import kotlinx.android.synthetic.main.frag_register_area5.cb_density_3
import kotlinx.android.synthetic.main.frag_register_area5.cb_density_4
import kotlinx.android.synthetic.main.frag_register_area5.cb_density_5
import kotlinx.android.synthetic.main.frag_register_area5.v_density_1
import kotlinx.android.synthetic.main.frag_register_area5.v_density_2
import kotlinx.android.synthetic.main.frag_register_area5.v_density_3
import kotlinx.android.synthetic.main.frag_register_area5.v_density_4
import kotlinx.android.synthetic.main.frag_register_area5.v_density_5
import java.util.*

class NewReview2Fragment : Fragment() {
    var checkboxArray = arrayOf<CheckBox>()
    var viewArray = arrayOf<TextView>()
    var density = 0

    companion object {
        var frag: NewReview2Fragment? = null

        fun getInstance() : NewReview2Fragment {
            if (frag == null) {
                frag = NewReview2Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_new_review2, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_hello.text = String.format(getString(R.string.new_review_density), SDF.dateWeek.format(Date()), NewReviewActivity.curReview.area?.name)

        checkboxArray = arrayOf(cb_density_1, cb_density_2, cb_density_3, cb_density_4, cb_density_5)
        viewArray = arrayOf(v_density_1, v_density_2, v_density_3, v_density_4, v_density_5)

        for (v in viewArray) {
            v.setOnClickListener(densityOnClickListener)
        }
    }

    private val densityOnClickListener = View.OnClickListener { view ->
        when (view?.id) {
            R.id.v_density_1 -> checkDensity(1)
            R.id.v_density_2 -> checkDensity(2)
            R.id.v_density_3 -> checkDensity(3)
            R.id.v_density_4 -> checkDensity(4)
            R.id.v_density_5 -> checkDensity(5)
        }
    }

    private fun checkDensity(n: Int) {
        for (i in 0..4) {
            checkboxArray[i].isChecked = i < n
        }
        density = n
    }
}