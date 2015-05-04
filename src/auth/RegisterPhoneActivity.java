package auth;

import static cn.smssdk.framework.utils.R.getStringRes;

import com.ehelp.esos.R;

import view.MaterialDialog;
import view.materialedittext.MaterialEditText;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPhoneActivity extends ActionBarActivity {

	public static Activity RegisterPhoneActivity;
	private Toolbar mToolbar;
	private MaterialDialog mMaterialDialog;
	private MaterialEditText phone_edit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_phone);
		RegisterPhoneActivity = this;
		init();
		setSMSSDK();
	}

	private void init() {
		setToolBar();
		
		final Button phoneButton = (Button) findViewById(R.id.phone_button);
		phoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMaterialDialog = new MaterialDialog(RegisterPhoneActivity.this)
			    .setTitle("ȷ���ֻ�����")
			    .setMessage("���ǽ�������֤����ŵ�������룺\n��+86�� " + phone_edit.getText().toString())
			    .setPositiveButton("ȷ��", new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            mMaterialDialog.dismiss();
			            
			            SMSSDK.getVerificationCode("86",phone_edit.getText().toString());
			            
						// ҳ����ת����֤��ҳ��
						Intent intent = new Intent(RegisterPhoneActivity.this,
								IdentifyActivity.class);
						intent.putExtra("phone", phone_edit.getText().toString()); 
						RegisterPhoneActivity.this.startActivity(intent);
			        }
			    })
			    .setNegativeButton("ȡ��", new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            mMaterialDialog.dismiss();

			        }
			    });
				mMaterialDialog.show();
			}
		});
		
		phone_edit = (MaterialEditText) findViewById(R.id.phone_edit);
		
		TextWatcher watcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() != 11){
					phoneButton.setEnabled(false);
				}else{
					phoneButton.setEnabled(true);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};
		
		phone_edit.addTextChangedListener(watcher);

		String html = "����Ϸ���ע�ᡱ��ť����ʾ��ͬ��";
		html += "<a href='http://www.baidu.com'>�����������ɼ�����Э�顷</a>";
		CharSequence charSequence = Html.fromHtml(html);

		TextView license = (TextView) findViewById(R.id.license);
		license.setText(charSequence);
		license.setMovementMethod(LinkMovementMethod.getInstance());

	}
	
	private void setSMSSDK(){
		SMSSDK.initSDK(this,"494a1aeb2935","07ecef839c58ef3f7329542164202d80");
	}
	
	EventHandler eh=new EventHandler(){

		@Override
		public void afterEvent(int event, int result, Object data) {
			
			Message msg = new Message();
			msg.arg1 = event;
			msg.arg2 = result;
			msg.obj = data;
			handler.sendMessage(msg);
		}
		
	};
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//�ύ��֤��ɹ�
					Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�", Toast.LENGTH_SHORT).show();
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "��֤���Ѿ�����", Toast.LENGTH_SHORT).show();
				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//����֧�ַ�����֤��Ĺ����б�
					Toast.makeText(getApplicationContext(), "��ȡ�����б�ɹ�", Toast.LENGTH_SHORT).show();
					
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(RegisterPhoneActivity.this, "smssdk_network_error");
				Toast.makeText(RegisterPhoneActivity.this, "��֤�����", Toast.LENGTH_SHORT).show();
				if (resId > 0) {
					Toast.makeText(RegisterPhoneActivity.this, resId, Toast.LENGTH_SHORT).show();
				}
			}
			
		}
		
	};

	private void setToolBar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		// toolbar.setLogo(R.drawable.ic_launcher);
		mToolbar.setTitle("ʹ���ֻ���ע��");// �������������setSupportActionBar֮ǰ����Ȼ����Ч
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
	
	public void onResume() {
		super.onResume();
		SMSSDK.registerEventHandler(eh);
	}

	public void onPause() {
		super.onPause();
		SMSSDK.unregisterEventHandler(eh);
	}
	

}
