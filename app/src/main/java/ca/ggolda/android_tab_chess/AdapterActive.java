package ca.ggolda.android_tab_chess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

public class AdapterActive extends ArrayAdapter<InstanceGame> {

    String mUserID;
    FirebaseAuth mFirebaseAuth;

    public AdapterActive(Context context, int resource, List<InstanceGame> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.card_game, parent, false);
        }

        // Get UserID
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUserID = user.getUid();


        final InstanceGame current = getItem(position);

        final TextView opponent = (TextView) convertView.findViewById(R.id.match_id);

        // Set name in list to opponent's
        if (mUserID.equals(current.getWhite())) {
            if (current.getUsername_black() != null){
                opponent.setText("User: " + current.getUsername_black());
            } else {
                opponent.setText("waiting...");
            }
        } else if (mUserID.equals(current.getBlack())) {
            opponent.setText("User: " + current.getUsername_white());
        }


//        convertView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Log.e("GAMESET",  "withintent"+current.getMatch_id());
//
//                Intent intent = new Intent(getContext(), BoardActivity.class);
//                intent.putExtra("MATCH_ID", current.getMatch_id());
//                getContext().startActivity(intent);
//
//            }
//        });




        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Get parent activity, set container view
                LobbyActivity lobbyActivity = (LobbyActivity) getContext();

                BoardFragment boardFragment = new BoardFragment();
                Bundle bundle = new Bundle();
                bundle.putString("gameset", current.getBoard());
                bundle.putString("match_id", current.getMatch_id());
                boardFragment.setArguments(bundle);

                lobbyActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, boardFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });


        return convertView;


    }


}
