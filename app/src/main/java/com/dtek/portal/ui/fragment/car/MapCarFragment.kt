package com.dtek.portal.ui.fragment.car

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.dtek.portal.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions





class MapCarFragment : Fragment(), OnMapReadyCallback {

    @BindView(R.id.map_view)
    private lateinit var mMap: GoogleMap

    private var unbinder: Unbinder? = null

    companion object {

        private val TAG = "MapCarFragment"
        private val ARG_MAP_CAR = "map_car"


        fun newInstance(/*CarOrder carOrder*/): MapCarFragment {
            val args = Bundle()
            //        args.putParcelable(ARG_MAP_CAR, carOrder);

            val fragment = MapCarFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_order_car_map, container, false)
        unbinder = ButterKnife.bind(this, v)

//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val mMap = activity!!.fragmentManager.findFragmentById(R.id.map_view) as MapFragment
//        mMap.getMapAsync(this)

        return v
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }
}