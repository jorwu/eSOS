package activity;

import view.CircleImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.ehelp.esos.R;
import com.lidroid.xutils.BitmapUtils;

import adapter.DrawerListAdapter;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener, AMapLocationListener, OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView list_1;
	private ListView list_2;
	private String[] str;
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;
	private LatLng mLocation;
	private LocationManagerProxy mLocationManagerProxy;
	private Button meButton;
	private Button inboxButton;
	private Button hButton;
	private Button sButton;
	private BitmapUtils bitmapUtils;
	private CircleImageView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultLoadingImage(R.drawable.contact_48dp);
		init();

	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			setUpMap();
		}
		
		head = (CircleImageView) findViewById(R.id.head);
		bitmapUtils.display(head, "http://www.qqzhi.com/uploadpic/2014-11-12/190218795.jpg");

		mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);

		list_1 = (ListView) findViewById(R.id.listview_1);
		str = new String[] { "基本信息", "医疗急救卡", "紧急联系人" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_1, str);
		list_1.setAdapter(adapter);
		list_1.setOnItemClickListener(this);

		list_2 = (ListView) findViewById(R.id.listview_2);
		list_2.setAdapter(new DrawerListAdapter(this));
		list_2.setOnItemClickListener(this);

		meButton = (Button) findViewById(R.id.Me_Button);
		inboxButton = (Button) findViewById(R.id.Inbox_Button);
		hButton = (Button) findViewById(R.id.H_Button);
		sButton = (Button) findViewById(R.id.S_Button);
		meButton.setOnClickListener(this);
		inboxButton.setOnClickListener(this);
		hButton.setOnClickListener(this);
		sButton.setOnClickListener(this);
	}

	/**
	 * amap设置
	 */
	private void setUpMap() {

		mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);

		aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 16));

		mUiSettings.setZoomControlsEnabled(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();

		mLocationManagerProxy = LocationManagerProxy.getInstance(this);

		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, -1, 15, this);

		mLocationManagerProxy.setGpsEnable(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		}
		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null
				&& amapLocation.getAMapException().getErrorCode() == 0) {

			Double geoLat = amapLocation.getLatitude();
			Double geoLng = amapLocation.getLongitude();
			mLocation = new LatLng(geoLat, geoLng);

			aMap.addMarker(new MarkerOptions().position(mLocation).icon(
					BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

			aMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(new CameraPosition(mLocation,
							(float) 14.5, 0, 0)));
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.Me_Button:
			mDrawerLayout.openDrawer(Gravity.LEFT);
			break;

		case R.id.Inbox_Button:
//			 Intent intentInbox = new Intent(MainActivity.this,
//			 ListActivity.class);
//			 startActivity(intentInbox);
			Intent intentInbox = new Intent(MainActivity.this,
					MessageActivity.class);
			startActivity(intentInbox);

			break;

		case R.id.H_Button:
			Intent intent0 = new Intent(MainActivity.this,
					CountDownActivity.class);
			startActivity(intent0);
			break;

		case R.id.S_Button:
			Intent intent1 = new Intent(MainActivity.this,
					CountDownActivity.class);
			startActivity(intent1);
			break;

		}

	}

}
