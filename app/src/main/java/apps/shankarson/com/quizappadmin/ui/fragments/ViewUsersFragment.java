package apps.shankarson.com.quizappadmin.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.model.User;
import apps.shankarson.com.quizappadmin.ui.adapters.ViewUsersAdapter;

public class ViewUsersFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_users, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        getUsers();
    }

    private void getUsers(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/getUsers.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                //.e("message", ""+response.toString());
                ArrayList<User> userList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = new JSONArray(jsonObject.get("content").toString());


                    for(int i=0;i<jsonArray.length();i++){
                        User user = new User();
                        JSONObject userObj = (JSONObject) jsonArray.get(i);
                        user.setName(userObj.get("name").toString());

                        Log.e("users", userObj.get("ID") + " " + userObj.get("name"));

                        userList.add(user);

                    }

                    Log.e("message", ""+jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                    RecyclerView recyclerView = getActivity().findViewById(R.id.view_users_rv);
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                    ViewUsersAdapter recyclerAdapter = new ViewUsersAdapter(userList);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("An","Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void showData(){
       // ViewUsersAdapter viewUsersAdapter = new ViewUsersAdapter()
        //RecyclerView rv = getActivity().findViewById(R.id.view_users_rv);
    }
}
