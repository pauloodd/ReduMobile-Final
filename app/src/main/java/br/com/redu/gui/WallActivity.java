package br.com.redu.gui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import br.com.redu.ReduMobile;
import br.com.redu.R;

public abstract class WallActivity extends AppCompatActivity {

	public ReduMobile application;

	public static final class DestructionIntent {
		public static final String ACTION_DESTROYING_ACTIVITY = "DESTROYING_ACTIVITY";
	}

	private final class DestructionReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}

	private DestructionReceiver destructionReceiver;

	protected abstract void loadBreadcrumb();

	protected abstract void loadStatuses();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		application = ReduMobile.getInstance();

		super.onCreate(savedInstanceState);

		destructionReceiver = new DestructionReceiver();

		registerReceiver(destructionReceiver, new IntentFilter(
				DestructionIntent.ACTION_DESTROYING_ACTIVITY));

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(destructionReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tab_menu, menu);
		return true;
	}

	protected abstract void updateStatuses();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_home:
				Intent intentHome = new Intent(this, UserWallActivity.class);
				intentHome.putExtra("userId", application.getUserLogin());

				startActivity(intentHome);
				break;
			case R.id.menu_refresh:

				ActionMenuItemView locButton = (ActionMenuItemView) findViewById(R.id.menu_refresh);
				if(locButton != null){
					Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
					locButton.startAnimation(rotation);
				}

				Toast.makeText(this, "Atualizando timeline...", Toast.LENGTH_SHORT).show();
				updateStatuses();
				break;
			case R.id.menu_logoff:
				application.deleteUserDate();

				sendBroadcast(new Intent(
						DestructionIntent.ACTION_DESTROYING_ACTIVITY));
				startActivity(new Intent(this, LoginActivity.class));
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateStatuses();
		ActionMenuItemView locButton = (ActionMenuItemView) findViewById(R.id.menu_refresh);
		if(locButton != null){
			Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
			locButton.startAnimation(rotation);
		}
	}
}
