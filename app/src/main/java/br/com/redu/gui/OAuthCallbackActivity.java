package br.com.redu.gui;

import br.com.redu.R;
import br.com.redu.ReduMobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

public final class OAuthCallbackActivity extends Activity {
	private ReduMobile application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			super.onCreate(savedInstanceState);

			setContentView(R.layout.oauth_callback_activity);

			application = ReduMobile.getInstance();

			String authorizationUrl = application.getClient().getAuthorizationUrl();

			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(authorizationUrl)));
		}
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Intent i = new Intent(this, LoginActivity.class);

		Uri uri = intent.getData();
		if (uri != null) {
			if (uri.toString().contains("error=access_denied")) {
				Toast.makeText(
						this,
						"Você deve autorizar o acesso deste aplicativo à sua conta",
						Toast.LENGTH_LONG).show();
			} else {
				if (application.captureUserLogin(uri)) {
					i = new Intent(this, UserWallActivity.class);
					i.putExtra("userId", application.getUserLogin());
				} else {
					Toast.makeText(
							this,
							"Ocorreu um erro ao tentar obter dados do usuário. Tente novamente",
							Toast.LENGTH_LONG).show();
				}
			}
		} else {
			Toast.makeText(
					this,
					"Ocorreu um erro ao tentar obter dados do usuário. Tente novamente",
					Toast.LENGTH_LONG).show();
		}

		startActivity(i);
		finish();
	}
}
