package auth;

import org.json.JSONException;
import org.json.JSONObject;

import view.MaterialDialog;
import view.materialedittext.MaterialEditText;

import com.ehelp.esos.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import activity.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends ActionBarActivity {

	public static Activity loginPhoneActivity;
	private Toolbar mToolbar;
	private MaterialEditText password_edit;
	private MaterialEditText phone_edit;
	private String phone;
	private String password;
	private MaterialDialog mMaterialDialog_1;
	private MaterialDialog mMaterialDialog_2;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		loginPhoneActivity = this;
		init();
	}

	private void init() {
		setToolBar();

		phone_edit = (MaterialEditText) findViewById(R.id.phone_edit);
		password_edit = (MaterialEditText) findViewById(R.id.password_edit);

		Button finishButton = (Button) findViewById(R.id.finish_button);
		finishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});

	}

	private void setToolBar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		// toolbar.setLogo(R.drawable.ic_launcher);
		mToolbar.setTitle("ʹ���ֻ��ŵ�½");// �������������setSupportActionBar֮ǰ����Ȼ����Ч
		// toolbar.setSubtitle("������");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
	
	private void login() {
		phone = phone_edit.getText().toString();
		password = password_edit.getText().toString();
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("cellPhone", phone);
			jsonObject.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestParams params = new RequestParams();
		params.addBodyParameter("fields", jsonObject.toString());

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				"http://120.24.208.130:3333/api/auth/login", params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						System.out.println("���ӷ�����ʧ��");
						mMaterialDialog_2 = new MaterialDialog(LoginActivity.this)
					    .setTitle("���粻��")
					    .setMessage("�����豸�������Ӳ����������������硣")
					    .setPositiveButton("ȷ��", new View.OnClickListener() {
					        @Override
					        public void onClick(View v) {
					        	mMaterialDialog_2.dismiss();
					        }
					    });
						mMaterialDialog_2.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

						try {
							JSONObject replyObject = new JSONObject(arg0.result);
							String state = replyObject.getString("state");
							if(state.equals("true")){
								SharedPreferences preferences = getSharedPreferences(
										"e_help", Context.MODE_PRIVATE);
								Editor editor = preferences.edit();
								editor.putString("cellPhone", phone);
								editor.putString("password", password);
								editor.commit();
								
								System.out.println("��¼�ɹ�");
								
								//ִ�е�½
//								App myApp = ((App) getApplicationContext());
//								myApp.login();
								
								// ҳ����ת����ҳ��
								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								LoginActivity.this.startActivity(intent);
								StartActivity.StartActivity.finish();
								finish();
								
							}else{
								System.out.println("��¼ʧ��");
								mMaterialDialog_1 = new MaterialDialog(LoginActivity.this)
							    .setTitle("��¼ʧ��")
							    .setMessage("���ĵ�¼����ʧ�ܣ����������˻���������Ϣ�����µ�¼��")
							    .setPositiveButton("ȷ��", new View.OnClickListener() {
							        @Override
							        public void onClick(View v) {
							        	mMaterialDialog_1.dismiss();

							        }
							    });
								mMaterialDialog_1.show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
	}

}
