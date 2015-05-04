package activity;

import java.util.ArrayList;

import utils.VibratorUtil;
import view.RippleBackground;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.ehelp.esos.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

public class CountDownActivity extends ActionBarActivity {

	private Button centerNum;
	private int time;
	private int T;
	Handler countDown;
	Runnable myRunnable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_countdown);
		init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		countDown.removeCallbacks(myRunnable);
	}

	@Override
	protected void onResume() {
		super.onResume();
		final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
		rippleBackground.startRippleAnimation();

		time = 4;
		T = 4;

		countDown = new Handler();
		myRunnable = new Runnable() {
			@Override
			public void run() {
				if (time == 0) {
					// Intent intent = new Intent(CountDownActivity.this,
					// SendHealthHelp.class);
					// startActivity(intent);
					finish();
				} else if (time == T) {
					T++;
					countDown.postDelayed(this, 500);
				} else {
					time--;
					centerNum.setText("" + time);
					centerNum();
					VibratorUtil.Vibrate(CountDownActivity.this, 400);
					countDown.postDelayed(this, 1000);
				}
			}
		};

		countDown.post(myRunnable);
	}

	private void init() {

		centerNum = (Button) findViewById(R.id.centerNum);

		Button cencel = (Button) findViewById(R.id.cencel_button);
		cencel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void centerNum() {
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(400);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		ArrayList<Animator> animatorList = new ArrayList<Animator>();
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(centerNum,
				"ScaleX", 0f, 1.2f, 1f);
		animatorList.add(scaleXAnimator);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(centerNum,
				"ScaleY", 0f, 1.2f, 1f);
		animatorList.add(scaleYAnimator);
		animatorSet.playTogether(animatorList);
		centerNum.setVisibility(View.VISIBLE);
		animatorSet.start();
	}
}
