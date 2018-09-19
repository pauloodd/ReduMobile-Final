package br.com.redu.gui.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.redu.oauth.ReduClient;
import br.com.redu.entity.Status;
import br.com.redu.entity.User;

public class UserWallAsyncTask extends AsyncTask<Void, Void, Status> {
    private Context context;
    private ProgressDialog dialog;
    private static ReduClient client;
    private String userId;

    public UserWallAsyncTask(Context context, ReduClient client, String userId){
        this.context = context;
        this.client = client;
        this.userId = userId;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Carregando dados...", true, true);
    }

    @Override
    protected br.com.redu.entity.Status doInBackground(Void... voids) {
        User user = client.getUser(userId);
        String userFullName = user.getFirstName() + " " + user.getLastName();
//        TextView lblFullName = (TextView) context.findViewById(R.id.userWallActivityLblFullName);
//        lblFullName.setText(userFullName);
        return null;
    }
}
