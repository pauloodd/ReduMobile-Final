package br.com.redu.gui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.redu.oauth.ReduClient;
import br.com.redu.R;
import br.com.redu.ReduMobile;
import br.com.redu.adapter.StatusAdapter;
import br.com.redu.entity.Lecture;
import br.com.redu.entity.Status;

public final class LectureWallActivity extends WallActivity {
	private ReduMobile application;
	private ReduClient client;
	private boolean firstStart;
	private View footerView;
	private int lectureId;
	private volatile String lectureName;
	private PullToRefreshListView listTimeline;
	private StatusAdapter listTimelineAdapter;
	private volatile boolean loadingMore;
	private final int MAX_NUM_PAGES = 25;
	private volatile int page;
	private ArrayList<Status> timeline;
	private boolean statusesLoaded;

	@Override
	protected void loadBreadcrumb() {

		new AsyncTask<Void, Void, Lecture>() {

			@Override
			protected Lecture doInBackground(Void... voids) {
				return  client.getLecture(String.valueOf(lectureId));
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Lecture lecture) {
				if(lecture != null){
					String lectureName = lecture.getName();
					ImageView imgLecture = (ImageView) findViewById(R.id.lectureWallActivityImgLecture);
					imgLecture.setVisibility(View.VISIBLE);
					TextView lblName = (TextView) findViewById(R.id.lectureWallActivityLblName);
					lblName.setText(lectureName);
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
				ArrayList<br.com.redu.entity.Status> newStatuses =client.getLectureStatuses(
						String.valueOf(lectureId), page, false);
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

		setContentView(R.layout.lecture_wall_activity);

		application = ReduMobile.getInstance();

		client = application.getClient();

		lectureId = getIntent().getIntExtra("lectureId", -1);
		if (lectureId == -1) {
			throw new RuntimeException("A id da aula não foi informada");
		}
		lectureName = getIntent().getStringExtra("lectureName");

		loadBreadcrumb();

		timeline = new ArrayList<Status>();

		listTimelineAdapter = new StatusAdapter(this, timeline);

		footerView = LayoutInflater.from(this).inflate(
				R.layout.list_statuses_footer, null);

		listTimeline = (PullToRefreshListView) findViewById(R.id.lectureWallActivityListStatuses);
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
						Toast.makeText(LectureWallActivity.this, "Timeline carregada por completo", Toast.LENGTH_SHORT).show();
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
				ArrayList<br.com.redu.entity.Status> newStatuses =client.getLectureStatuses(
						String.valueOf(lectureId), 1, false);
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
				Intent intent = new Intent(this, PostOnLectureWallActivity.class);
				intent.putExtra("lectureId", lectureId);
				intent.putExtra("lectureName", lectureName);

				startActivity(intent);

				break;

		}
		return super.onOptionsItemSelected(item);
	}
}