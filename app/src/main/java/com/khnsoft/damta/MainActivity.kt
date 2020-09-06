package com.khnsoft.damta

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.damta.data.*
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.MyLogger
import com.khnsoft.damta.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_navigate
import kotlinx.android.synthetic.main.activity_main.cb_bookmark
import kotlinx.android.synthetic.main.activity_main.cb_density_1
import kotlinx.android.synthetic.main.activity_main.cb_density_2
import kotlinx.android.synthetic.main.activity_main.cb_density_3
import kotlinx.android.synthetic.main.activity_main.cb_density_4
import kotlinx.android.synthetic.main.activity_main.cb_density_5
import kotlinx.android.synthetic.main.activity_main.cb_thumb
import kotlinx.android.synthetic.main.activity_main.tv_address
import kotlinx.android.synthetic.main.activity_main.tv_name
import kotlinx.android.synthetic.main.activity_main.tv_thumb
import kotlinx.android.synthetic.main.activity_main.tv_type
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.lang.Exception
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    var checkboxArray = arrayOf<CheckBox>()
    val filter = hashMapOf(
        AreaType.OPENED to true,
        AreaType.CLOSED to true,
        AreaType.FULLYCLOSED to true
    )
    val lPOI = ArrayList<MapPOIItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                MyLogger.i("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e : PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e : NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        */

        checkboxArray = arrayOf(cb_density_1, cb_density_2, cb_density_3, cb_density_4, cb_density_5)

        btn_close_banner.setOnClickListener {
            main_banner.visibility = View.GONE
        }

        btn_search.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivityForResult(intent, SearchActivity.RC_ADDRESS)
        }

        btn_filter.setOnClickListener {
            view_filter.visibility = View.VISIBLE
            cb_type_opened.isChecked = filter[AreaType.OPENED] == true
            cb_type_closed.isChecked = filter[AreaType.CLOSED] == true
            cb_type_fullyclosed.isChecked = filter[AreaType.FULLYCLOSED] == true
        }

        btn_close.setOnClickListener {
            view_filter.visibility = View.GONE
        }

        btn_confirm.setOnClickListener {
            filter[AreaType.OPENED] = cb_type_opened.isChecked
            filter[AreaType.CLOSED] = cb_type_closed.isChecked
            filter[AreaType.FULLYCLOSED] = cb_type_fullyclosed.isChecked
            view_filter.visibility = View.GONE
            refreshArea()
        }

        btn_gps.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeTop() {
                view_result.visibility = View.VISIBLE
                val areas = Area.searchByName(this@MainActivity, "")
                val adapter = AreaRecyclerAdapter(areas)
                val lm = LinearLayoutManager(this@MainActivity)
                result_container.layoutManager = lm
                result_container.adapter = adapter
            }

            override fun onClick() {
                attachLocation()
            }
        })

        btn_cancel_result.setOnClickListener {
            view_result.visibility = View.GONE
        }

        btn_cancel_place.setOnClickListener {
            view_place.visibility = View.GONE
        }

        btn_register_area.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterAreaWarningActivity::class.java)
            startActivity(intent)
        }

        btn_mypage.setOnClickListener {
            val intent = Intent(this@MainActivity, MypageActivity::class.java)
            startActivity(intent)
        }

        map_view.setMapViewEventListener(mapViewEventListener)
        map_view.setPOIItemEventListener(poiItemListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SearchActivity.RC_ADDRESS -> {
                val address = data?.getStringExtra(SearchActivity.EXTRA_ADDRESS) ?: return
                tv_search.setText(address)
                val x = data.getDoubleExtra(SearchActivity.EXTRA_X, 0.0)
                val y = data.getDoubleExtra(SearchActivity.EXTRA_Y, 0.0)

                map_view.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(y, x), false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshArea()
    }

    private val mapViewEventListener = object : MapView.MapViewEventListener {
        override fun onMapViewDoubleTapped(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewInitialized(mapView: MapView?) {
            refreshArea()
        }

        override fun onMapViewDragStarted(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewMoveFinished(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewCenterPointMoved(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewDragEnded(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewSingleTapped(mapView: MapView?, mapPoint: MapPoint?) { }

        override fun onMapViewZoomLevelChanged(mapView: MapView?, mapPoint: Int) { }

        override fun onMapViewLongPressed(mapView: MapView?, mapPoint: MapPoint?) { }
    }

    private fun refreshArea() {
        map_view.removePOIItems(lPOI.toTypedArray())
        lPOI.clear()

        val areas = Area.searchByName(this@MainActivity, "")
        area@ for (area in areas) {
            when (area.type) {
                AreaType.OPENED -> if (filter[AreaType.OPENED] != true) continue@area
                AreaType.CLOSED -> if (filter[AreaType.CLOSED] != true) continue@area
                AreaType.FULLYCLOSED -> if (filter[AreaType.FULLYCLOSED] != true) continue@area
            }

            val poi = MapPOIItem()
            poi.itemName = area.name
            poi.mapPoint = MapPoint.mapPointWithGeoCoord(area.y, area.x)
            poi.markerType = MapPOIItem.MarkerType.CustomImage
            poi.customImageResourceId = when (area.type) {
                AreaType.OPENED -> R.drawable.type_opened
                AreaType.CLOSED -> R.drawable.type_closed
                AreaType.FULLYCLOSED -> R.drawable.type_fullyclosed
            }
            poi.tag = area.id
            poi.isShowCalloutBalloonOnTouch = false
            lPOI.add(poi)
            map_view.addPOIItem(poi)
        }
    }

    private val poiItemListener = object: MapView.POIItemEventListener {
        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) { }

        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) { }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) { }

        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            if (poiItem == null) {
                return
            }

            val area = Area.getById(this@MainActivity, poiItem.tag) ?: return

            btn_expand.setOnTouchListener(object: OnSwipeTouchListener(this@MainActivity) {
                override fun onSwipeTop() {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(Area.EXTRA_AREA_ID, area.id)
                    startActivity(intent)
                    view_place.visibility = View.GONE
                }
            })

            tv_type.text = when (area.type) {
                AreaType.OPENED -> getString(R.string.area_type_opened)
                AreaType.CLOSED -> getString(R.string.area_type_closed)
                AreaType.FULLYCLOSED -> getString(R.string.area_type_fullyclosed)
            }
            tv_name.text = area.name
            cb_bookmark.isChecked = Bookmark.isBookmarked(this@MainActivity, User.current, area)
            tv_address.text = area.address
            cb_thumb.isChecked = Thumb.isThumbed(this@MainActivity, User.current, area)
            tv_thumb.text = Thumb.getCountByArea(this@MainActivity, area).toString()
            checkDensity(area.density)

            cb_bookmark.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    Bookmark.add(this@MainActivity, User.current ?: return@setOnCheckedChangeListener, area)
                } else {
                    Bookmark.remove(this@MainActivity, User.current ?: return@setOnCheckedChangeListener, area)
                }
            }

            cb_thumb.setOnCheckedChangeListener { buttonView, isChecked ->
                MyLogger.d("@@@", "check ${User.current}")
                if (isChecked) {
                    Thumb.add(this@MainActivity, User.current ?: return@setOnCheckedChangeListener, area)
                } else {
                    Thumb.remove(this@MainActivity, User.current ?: return@setOnCheckedChangeListener, area)
                }
                tv_thumb.text = Thumb.getCountByArea(this@MainActivity, area).toString()
            }

            val reviews = Review.getByArea(this@MainActivity, area)
            if (reviews.size > 0) {
                tv_review_user_1.text = "${reviews[0].user?.nickname}(${reviews[0].user?.username})"
                tv_review_1.text = reviews[0].review

                if (reviews.size > 1) {
                    tv_review_user_2.text = "${reviews[1].user?.nickname}(${reviews[1].user?.username})"
                    tv_review_2.text = reviews[1].review
                }
            }

            btn_navigate.setOnClickListener {
                try {
                    val uri = Uri.parse("kakaomap://route?ep=${area.y},${area.x}&by=FOOT")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(uri)
                    startActivity(intent)
                } catch (e: Exception) {
                    val uri = Uri.parse("https://map.kakao.com/link/to/${area.address},${area.y},${area.x}")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(uri)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }

            btn_information.setOnClickListener {
                when (view_information.visibility) {
                    View.VISIBLE -> {
                        view_information.visibility = View.INVISIBLE
                    }
                    View.INVISIBLE -> {
                        view_information.visibility = View.VISIBLE
                    }
                }
            }

            view_place.visibility = View.VISIBLE
        }
    }

    private fun checkDensity(n: Double) {
        for (i in 0..4) {
            checkboxArray[i].isChecked = i < n
        }
    }

    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            map_view.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude), false)
            MyLogger.d("Main_Location", "Location changed")
            detachLocation()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String) {
        }

        override fun onProviderDisabled(provider: String) {
        }
    }

    private fun attachLocation() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5.0f, mLocationListener)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5.0f, mLocationListener)
            MyLogger.d("Main_Location", "Attach")
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun detachLocation() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            lm.removeUpdates(mLocationListener)
            MyLogger.d("Main_Location", "Detach")
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    inner class AreaRecyclerAdapter(val lArea: Array<Area>) :
            RecyclerView.Adapter<AreaRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val container = itemView.findViewById<LinearLayout>(R.id.item_container)
            val name = itemView.findViewById<TextView>(R.id.tv_name)
            val type = itemView.findViewById<TextView>(R.id.tv_type)
            val address = itemView.findViewById<TextView>(R.id.tv_address)
            val distance = itemView.findViewById<TextView>(R.id.tv_distance)
            val thumb = itemView.findViewById<CheckBox>(R.id.cb_thumb)
            val thumbCount = itemView.findViewById<TextView>(R.id.tv_thumb)
            val bookmark = itemView.findViewById<CheckBox>(R.id.cb_bookmark)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_main_map, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lArea.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val area = lArea[position]
            holder.name.text = area.name
            holder.type.text = when (area.type) {
                AreaType.OPENED -> getString(R.string.area_type_opened)
                AreaType.CLOSED -> getString(R.string.area_type_closed)
                AreaType.FULLYCLOSED -> getString(R.string.area_type_fullyclosed)
            }
            holder.address.text = area.address
            // TODO("distance")
            holder.thumb.isChecked = Thumb.isThumbed(this@MainActivity, User.current, area)
            holder.thumbCount.text = Thumb.getCountByArea(this@MainActivity, area).toString()
            holder.bookmark.isChecked = Bookmark.isBookmarked(this@MainActivity, User.current, area)
        }
    }
}