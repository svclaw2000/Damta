package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.khnsoft.damta.data.Area
import com.khnsoft.damta.data.Image
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.AreaType
import kotlinx.android.synthetic.main.activity_register_area.*
import kotlinx.android.synthetic.main.activity_register_area.btn_next
import kotlinx.android.synthetic.main.activity_register_area.popup_warning
import kotlinx.android.synthetic.main.frag_register_area1.*
import kotlinx.android.synthetic.main.frag_register_area2.*
import kotlinx.android.synthetic.main.frag_register_area3.*
import kotlinx.android.synthetic.main.frag_register_area4.*
import kotlinx.android.synthetic.main.popup_warning.*

class RegisterAreaActivity : AppCompatActivity() {
    var curFrag = 1
    var frag1 = RegisterArea1Fragment.getInstance()
    var frag2 = RegisterArea2Fragment.getInstance()
    var frag3 = RegisterArea3Fragment.getInstance()
    var frag4 = RegisterArea4Fragment.getInstance()
    var frag5 = RegisterArea5Fragment.getInstance()
    var frag6 = RegisterArea6Fragment.getInstance()

    companion object {
        val curArea = Area()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_area)

        btn_back.setOnClickListener {
            when (curFrag) {
                1 -> {
                    finish()
                }
                6 -> {
                    btn_next.text = getString(R.string.next)
                    btn_next.setTextColor(getColor(R.color.dark))
                    callPage(curFrag - 1)
                }
                else -> {
                    callPage(curFrag - 1)
                }
            }
        }

        btn_next.setOnClickListener {
            when (curFrag) {
                1 -> {
                    if (frag1.validateInformation()) {
                        curArea.name = et_name.text.toString()
                        callPage(curFrag + 1)
                    } else {
                        popupWarning(getString(R.string.warning_empty_field))
                    }
                }
                2 -> {
                    if (frag2.validateInformation()) {
                        curArea.address = et_address.text.toString()
                        curArea.x = frag2.x ?: return@setOnClickListener
                        curArea.y = frag2.y ?: return@setOnClickListener
                        callPage(curFrag + 1)
                    } else {
                        popupWarning(getString(R.string.warning_empty_field))
                    }
                }
                3 -> {
                    if (frag3.validateInformation()) {
                        curArea.ashtray = cb_ashtray.isChecked
                        curArea.vent = cb_vent.isChecked
                        curArea.bench = cb_bench.isChecked
                        curArea.machine = cb_machine.isChecked
                        callPage(curFrag + 1)
                    } else {
                        popupWarning(getString(R.string.warning_empty_field))
                    }
                }
                4 -> {
                    if (frag4.validateInformation()) {
                        when (rg_type.checkedRadioButtonId) {
                            R.id.rb_opened -> curArea.type = AreaType.OPENED
                            R.id.rb_closed -> curArea.type = AreaType.CLOSED
                            R.id.rb_fullyclosed -> curArea.type = AreaType.FULLYCLOSED
                        }
                        callPage(curFrag + 1)
                    } else {
                        popupWarning(getString(R.string.warning_empty_field))
                    }
                }
                5 -> {
                    if (frag5.validateInformation()) {
                        curArea.density = frag5.density.toDouble()
                        btn_next.text = getString(R.string.finish)
                        btn_next.setTextColor(getColor(R.color.highlight))
                        callPage(curFrag + 1)
                    } else {
                        popupWarning(getString(R.string.warning_empty_field))
                    }
                }
                6 -> {
                    val areaId = curArea.add(this@RegisterAreaActivity)
                    if (areaId != null) {
                        if (frag6.curImage != null) {
                            val image = Image(
                                area = Area.getById(this@RegisterAreaActivity, areaId),
                                image = frag6.curImage
                            )
                            val result = image.add(this@RegisterAreaActivity)

                            if (result) {
                                val intent = Intent(this@RegisterAreaActivity, RegisterAreaActivityThank::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            val intent = Intent(this@RegisterAreaActivity, RegisterAreaActivityThank::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else -> {
                    callPage(curFrag + 1)
                }
            }
        }

        btn_confirm.setOnClickListener {
            removeWarning()
        }

        callPage(1)
    }

    private fun popupWarning(msg: String) {
        tv_warning.text = msg
        popup_warning.visibility = View.VISIBLE
    }

    private fun removeWarning() {
        popup_warning.visibility = View.GONE
    }

    private fun callPage(idx: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        when (idx) {
            1 -> {
                transaction.replace(R.id.frag_container, RegisterArea1Fragment.getInstance())
                transaction.commit()
            }
            2 -> {
                transaction.replace(R.id.frag_container, RegisterArea2Fragment.getInstance())
                transaction.commit()
            }
            3 -> {
                transaction.replace(R.id.frag_container, RegisterArea3Fragment.getInstance())
                transaction.commit()
            }
            4 -> {
                transaction.replace(R.id.frag_container, RegisterArea4Fragment.getInstance())
                transaction.commit()
            }
            5 -> {
                transaction.replace(R.id.frag_container, RegisterArea5Fragment.getInstance())
                transaction.commit()
            }
            6 -> {
                transaction.replace(R.id.frag_container, RegisterArea6Fragment.getInstance())
                transaction.commit()
            }
        }

        curFrag = idx
    }
}