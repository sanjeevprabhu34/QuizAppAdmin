package apps.shankarson.com.quizappadmin.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

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

import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionOptionObj;
import apps.shankarson.com.quizappadmin.ui.StandaloneViews.AddOptionView;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionObj;
import apps.shankarson.com.quizappadmin.model.User;
import apps.shankarson.com.quizappadmin.ui.adapters.ViewUsersAdapter;

public class AddGeneralQuestionFragment extends Fragment implements GeneralQuestionObj.Interactor {
    private GeneralQuestionObj generalQuestionObj;
    private TextView percentageRemainignTv;
    private EditText questionEt ;
    private EditText startTimeEt;
    private EditText endTimeEt ;
    private ViewGroup container;
    private EditText optionText;
    private boolean isCorrectOption = false;
    private SwitchCompat correctAnswerSwitch;
    private Button doneBtn;
    private FloatingActionButton addBtn;
    private JSONObject singleQuestionJsonObj;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_general_question, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init();

       /* String timeStr = "26/05/2018 14:20";
        String format = "dd/mm/yyyy hh:mm";

        try {
            DateUtilities.StringTimeToDate(timeStr, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    private void init() throws JSONException {
         questionEt = getActivity().findViewById(R.id.questionEt);
        startTimeEt = getActivity().findViewById(R.id.startDateEt);
         endTimeEt = getActivity().findViewById(R.id.endDateEt);
         container = getActivity().findViewById(R.id.options_container);
         optionText = getActivity().findViewById(R.id.optionTextEt);

        percentageRemainignTv = getActivity().findViewById(R.id.optionPercentageRemaining);
        correctAnswerSwitch = getActivity().findViewById(R.id.optionSwitch);
        doneBtn = getActivity().findViewById(R.id.done_btn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalQuestionObj.IsQuestionSetCompleted();
            }
        });

        addBtn  = getActivity().findViewById(R.id.optionAddBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String optionTextStr = optionText.getText().toString();
                boolean isCorrect = true;
                float percentage  = 100;
                generalQuestionObj.checkForOptionCompleted(optionTextStr, isCorrectOption, percentage);


            }
        });

        correctAnswerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCorrectOption = isChecked;

            }
        });

        generalQuestionObj = new GeneralQuestionObj(this);


        questionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generalQuestionObj.setQuestion(s.toString());
                Log.e("editing", "It is editing "  +generalQuestionObj.getQuestion());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startTimeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                generalQuestionObj.setStartDateStr(s.toString());
                Log.e("editing", "start time is "  +generalQuestionObj.getStartDateStr());
            }
        });

        endTimeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                generalQuestionObj.setEndDateStr(s.toString());
                Log.e("editing", "end time is "  +generalQuestionObj.getEndDateStr());
            }
        });

        //getUsers();

        assembleSingleQuestionJsonObj();
    }

    private void assembleSingleQuestionJsonObj() throws JSONException {
        String Question = "Sample Question";
        JSONArray optionsJsonArray = new JSONArray();
        JSONObject option1 = new JSONObject();
        option1.put("answer", "option1");
        JSONObject option2 = new JSONObject();
        option2.put("answer", "option2");
        JSONObject option3 = new JSONObject();
        option3.put("answer", "option3");

        singleQuestionJsonObj = new JSONObject();
        singleQuestionJsonObj.put("Question", Question);
        singleQuestionJsonObj.put("optionList", optionsJsonArray);
        singleQuestionJsonObj.put("startDate", "30/05/2018 10:00");
        singleQuestionJsonObj.put("endDate", "30/05/2018 10:00");

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

    @Override
    public void optionTextNotComplete() {
        Log.e("options", "In Correctlt entered");
        Snackbar.make(addBtn, "In Correctlt entered", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void optionCorrectPriorityRemaining() {
        Log.e("Question","Question Priority Not Completed to 100%");
        Snackbar.make(addBtn, "Question Priority Not Completed to 100%", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void optionTextComplete(GeneralQuestionOptionObj generalQuestionOptionObj) {
        Log.e("options", "Correctlt entered");
        AddOptionView addOption = new AddOptionView(generalQuestionOptionObj,container);
    }

    @Override
    public void priorityPercentageRemaining(float perRemaining) {
        percentageRemainignTv.setText("Remaining "  +String.valueOf(100 - perRemaining));
    }

    @Override
    public void questionCompleted() {
        Log.e("Question", "Adding Question to Server Database ");
    }

    @Override
    public void questionNotCompleted(String reason) {
        Log.e("Question", reason);
        Snackbar.make(addBtn, reason, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}