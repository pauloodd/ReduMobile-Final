package br.com.redu.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.redu.oauth.ReduClient;
import br.com.redu.R;
import br.com.redu.adapter.StatusAdapter;
import br.com.redu.entity.Space;
import br.com.redu.entity.Status;

public final class SpaceWallActivity extends WallActivity {
	private ReduClient client;
	private boolean firstStart;
	private View footerView;
	private PullToRefreshListView listTimeline;
	private StatusAdapter listTimelineAdapter;
	private volatile boolean loadingMore;
	private final int MAX_NUM_PAGES = 25;
	private volatile int page;
	private int spaceId;
	private volatile String spaceName;
	private ArrayList<Status> timeline;
	private boolean timelineLoaded;

	@Override
	protected void loadBreadcrumb() {
		new AsyncTask<Void, Void, Space>() {

			@Override
			protected Space doInBackground(Void... voids) {
				return client.getSpace(String.valueOf(spaceId));
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Space space) {
				if(space != null){
					ImageView imgSpace = (ImageView) findViewById(R.id.spaceWallActivityImgSpace);
					imgSpace.setVisibility(View.VISIBLE);
					TextView lblName = (TextView) findViewById(R.id.spaceWallActivityLblName);
					lblName.setText(space.getName());
				}
			}
		}.execute();
	}

	@Override
	protected void loadStatuses() {

		new AsyncTask<Void, Void, ArrayList<Status>>() {

			@Override
			protected ArrayList<br.com.redu.entity.Status> doInBackground(Void... voids) {
				if(page > MAX_NUM_PAGES){
					loadingMore = false;
				}
				ArrayList<br.com.redu.entity.Status> newStatuses =client.getSpaceTimeline(
						String.valueOf(spaceId), page, false);
				page++;
				return newStatuses;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				footerView.findViewById(R.id.listTimelineFooterProgressBarMain).setVisibility(View.VISIBLE);
				footerView.findViewById(R.id.foot_load_more).setVisibility(View.GONE);
			}

			@Override
			protected void onPostExecute(ArrayList<br.com.redu.entity.Status> newStatus) {
				if(newStatus != null && !newStatus.isEmpty()){
					for (br.com.redu.entity.Status st : newStatus){
						if(!timeline.contains((br.com.redu.entity.Status) st)){
							timeline.add(st);
						}
					}
				}else{
					loadingMore = false;
				}
				listTimelineAdapter.notifyDataSetChanged();
				footerView.findViewById(R.id.listTimelineFooterProgressBarMain).setVisibility(View.GONE);
				footerView.findViewById(R.id.foot_load_more).setVisibility(View.VISIBLE);
				listTimeline.onRefreshComplete();
			}
		}.execute();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.space_wall_activity);

		client = application.getClient();

		spaceId = getIntent().getIntExtra("spaceId", -1);
		if (spaceId == -1) {
			throw new RuntimeException("A id da disciplina não foi informada");
		}
		spaceName = getIntent().getStringExtra("spaceName");

		loadBreadcrumb();

		timeline = new ArrayList<Status>();

		listTimelineAdapter = new StatusAdapter(this, timeline);

		footerView = LayoutInflater.from(this).inflate(
				R.layout.list_statuses_footer, null);

		listTimeline = (PullToRefreshListView) findViewById(R.id.spaceWallActivityListTimeline);
		listTimeline.addFooterView(footerView);
		listTimeline.setAdapter(listTimelineAdapter);

		View carregaMaisView = (View) findViewById(R.id.foot_load_more);
		if(carregaMaisView != null){
			carregaMaisView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (loadingMore) {
						loadStatuses();
					}else{
						Toast.makeText(SpaceWallActivity.this, "Timeline carregada por completo", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		listTimeline.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

			@Override
			public void onRefresh() {
				updateStatuses();
			}

		});

		page = 1;

		updateStatuses();

		listTimeline.onRefreshComplete();
	}

	@Override
	protected void updateStatuses() {
		new AsyncTask<Void, Void, ArrayList<Status>>() {

			@Override
			protected ArrayList<br.com.redu.entity.Status> doInBackground(Void... voids) {
				ArrayList<br.com.redu.entity.Status> newStatuses =client.getSpaceTimeline(
						String.valueOf(spaceId), 1, false);
				return newStatuses;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				footerView.findViewById(R.id.listTimelineFooterProgressBarMain).setVisibility(View.VISIBLE);
				footerView.findViewById(R.id.foot_load_more).setVisibility(View.GONE);
			}

			@Override
			protected void onPostExecute(ArrayList<br.com.redu.entity.Status> newStatus) {
				timeline.clear();
				timeline.addAll(newStatus);
				listTimelineAdapter.notifyDataSetChanged();
				footerView.findViewById(R.id.listTimelineFooterProgressBarMain).setVisibility(View.GONE);
				footerView.findViewById(R.id.foot_load_more).setVisibility(View.VISIBLE);
				listTimeline.setSelection(0);
				listTimeline.onRefreshComplete();
				page = 2;
			}
		}.execute();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_compose:
				Intent intent = new Intent(this, PostOnSpaceWallActivity.class);
				intent.putExtra("spaceId", spaceId);
				intent.putExtra("spaceName", spaceName);

				startActivity(intent);

				break;

		}
		return super.onOptionsItemSelected(item);
	}
}