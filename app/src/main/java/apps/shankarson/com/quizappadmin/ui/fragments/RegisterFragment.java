package apps.shankarson.com.quizappadmin.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import apps.shankarson.com.quizappadmin.Connectors.PostUrl;
import apps.shankarson.com.quizappadmin.R;


public class RegisterFragment extends Fragment implements PostUrl.Interactor{
    private HashMap<String, String> map;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
       final  EditText userNameTv = view.findViewById(R.id.username_et);
        final EditText nameTv = view.findViewById(R.id.name_et);
        final EditText passEt = view.findViewById(R.id.password_et);
        final EditText emailEt = view.findViewById(R.id.email_et2);
        final Button submitBtn = view.findViewById(R.id.submitBtn);
        final EditText telEt = view.findViewById(R.id.tel_et);
        final String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Register.php";
        map = new HashMap<>();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("username", userNameTv.getText().toString().trim());
                map.put("name", nameTv.getText().toString().trim());
                map.put("password", passEt.getText().toString().trim());
                map.put("email", emailEt.getText().toString().trim());
                map.put("tel", telEt.getText().toString().trim());


                PostUrl(getActivity(), url, map);
            }
        });
    }

    public void PostUrl(Context context, String url, final HashMap<String, String> params){
        // interactor = (Interactor) context;
        //developer branch push
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //progressBar.setVisibility(View.INVISIBLE);


                        //getting the whole json object from the response


                        Log.e("jsontest", response);


                        


                        //we have the array named hero inside the object
                        //so here we are getting that json array


                        //creating custom adapter object


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }



    @Override
    public void urlConnected() {
        Log.e("url", "connected");
    }
}
