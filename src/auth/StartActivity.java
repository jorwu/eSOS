package auth;

import com.ehelp.esos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends ActionBarActivity {
	
	public static Activity StartActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		StartActivity = this;
		init();
	}

	private void init() {

		Button loginButton = (Button) findViewById(R.id.login);
		Button registerButton = (Button) findViewById(R.id.register);

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ҳ����ת����¼ҳ��
				Intent intent = new Intent(StartActivity.this,
						LoginActivity.class);
				StartActivity.this.startActivity(intent);
			}
		});

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ҳ����ת��ע���ֻ���ҳ��
				Intent intent = new Intent(StartActivity.this,
						RegisterPhoneActivity.class);
				StartActivity.this.startActivity(intent);
			}
		});

	}

}
