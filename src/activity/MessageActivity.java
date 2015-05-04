package activity;

import view.CircleImageView;

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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MessageActivity extends ActionBarActivity implements
		OnClickListener {

	private Toolbar mToolbar;
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;
	private LatLng mLocation;
	private BitmapUtils bitmapUtils;
	private CircleImageView head;
	private TextView nickname;
	private TextView message;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
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

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		// toolbar.setLogo(R.drawable.ic_launcher);
		mToolbar.setTitle("消息详情");// 标题的文字需在setSupportActionBar之前，不然会无效
		// toolbar.setSubtitle("副标题");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		head = (CircleImageView) findViewById(R.id.head);
		nickname = (TextView) findViewById(R.id.nickname);
		message = (TextView) findViewById(R.id.message);
		
		bitmapUtils.display(head, "http://www.qqzhi.com/uploadpic/2014-11-12/190218795.jpg");
		nickname.setText("王金鑫");
		message.setText("ndjsaindjasidnisaindnsjan");
		
	}

	/**
	 * amap设置
	 */
	private void setUpMap() {

		mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);

		aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 16));

		mUiSettings.setZoomControlsEnabled(true);

		// 设置指南针是否显示
		mUiSettings.setCompassEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();

		// mLocation = new LatLng(geoLat, geoLng);
		//
		// aMap.addMarker(new MarkerOptions().position(mLocation).icon(
		// BitmapDescriptorFactory
		// .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		//
		// aMap.moveCamera(CameraUpdateFactory
		// .newCameraPosition(new CameraPosition(mLocation,
		// (float) 14.5, 0, 0)));
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
