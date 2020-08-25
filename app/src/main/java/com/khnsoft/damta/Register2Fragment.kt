package com.khnsoft.damta

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.khnsoft.damta.utils.SDF
import kotlinx.android.synthetic.main.frag_register2.*
import java.util.*

class Register2Fragment : Fragment() {
    companion object {
        var frag: Register2Fragment? = null

        fun getInstance() : Register2Fragment {
            if (frag == null) {
                frag = Register2Fragment().apply { arguments = Bundle() }
            }
            return frag!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_register2, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_birthday.setOnClickListener {
            val calendar = RegisterActivity.birthCalendar ?: Calendar.getInstance().apply {
                set(Calendar.YEAR, 1990)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 1)
            }

            DatePickerDialog(context!!, { view, year, month, dayOfMonth ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                et_birthday.setText(SDF.dateDot.format(calendar.time))
                RegisterActivity.birthCalendar = calendar
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }
    }
}