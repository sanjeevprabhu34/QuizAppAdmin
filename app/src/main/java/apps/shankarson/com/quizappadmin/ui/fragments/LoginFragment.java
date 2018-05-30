package apps.shankarson.com.quizappadmin.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import apps.shankarson.com.quizappadmin.R;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        final TextView emailTv = getActivity().findViewById(R.id.email_et);
        final TextView passwordEt = getActivity().findViewById(R.id.password_et);
        Button submitBtn = getActivity().findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTv.getText().toString();
                String pass = passwordEt.getText().toString();

                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);

                if(!email.equals("") &&!pass.equals("") ){
                    login(params);
                }else{
                    Toast.makeText(getActivity(), "e-Mail or password not filled", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void login(final Map params ){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        //http://localhost:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Login.php?email=ss@gg.com&password=1234567
        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Login.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                Log.e("message", response.toString());

                /*try {
                    JSONObject jsonObject = new JSONObject(response);
                    String statusStr = jsonObject.getString("status");

                    if(statusStr.equals("success")){
                        Toast.makeText(getActivity(), "Post created successfully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("An","Error :" + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

}
