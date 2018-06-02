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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.Utilities.DateUtilities;
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
        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        generalQuestionObj = new GeneralQuestionObj(this);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String optionTextStr = optionText.getText().toString();
                boolean isCorrect = true;
                float percentage  = 100;
                long startTime = 0;
                long endTime = 0;
                try {
                    startTime = DateUtilities.StringTimeToDate(startTimeEt.getText().toString(), "dd/mm/yyyy");
                    endTime = DateUtilities.StringTimeToDate(startTimeEt.getText().toString(), "dd/mm/yyyy");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                generalQuestionObj.setStartDateStr(startTime);
                generalQuestionObj.setEndDateStr(endTime);
                Log.e("editing", "start time is "  +generalQuestionObj.getStartDateStr());


                generalQuestionObj.checkForOptionCompleted(optionTextStr, isCorrectOption, percentage, startTime, endTime);
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
                generalQuestionObj.setEndDateStr(Long.parseLong(s.toString()));
                Log.e("editing", "end time is "  +generalQuestionObj.getEndDateStr());
            }
        });

        correctAnswerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCorrectOption = isChecked;
            }
        });

        questionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                generalQuestionObj.setQuestion(s.toString());
            }
        });

        //assembleSingleQuestionJsonObj(generalQuestionObj);
    }

    private void assembleSingleQuestionJsonObj(GeneralQuestionObj generalQuestionObj) throws JSONException {
        String Question = "What is the  Capital Of India";
        JSONArray optionsJsonArray = new JSONArray();

      /* JSONObject option1Obj = new JSONObject();
        option1Obj.put("optionname", "Mumbai" );
        option1Obj.put("iscorrect", false );
        option1Obj.put("priority", 0 );
        optionsJsonArray.put(option1Obj);
        JSONObject option2Obj = new JSONObject();
        option2Obj.put("optionname", "Banglore" );
        option2Obj.put("iscorrect", false );
        option2Obj.put("priority", 0 );
        optionsJsonArray.put(option2Obj);
        JSONObject option3Obj = new JSONObject();
        option3Obj.put("optionname", "Delhi" );
        option3Obj.put("iscorrect", true );
        option3Obj.put("priority", 100 );
        optionsJsonArray.put(option3Obj);

        singleQuestionJsonObj = new JSONObject();
        singleQuestionJsonObj.put("Question", Question);
        singleQuestionJsonObj.put("optionList", optionsJsonArray);
        try {
            singleQuestionJsonObj.put("startDate", DateUtilities.StringTimeToDate("30/05/2018 10:00", "dd/MM/yyyy hh:mm"));
            singleQuestionJsonObj.put("endDate", DateUtilities.StringTimeToDate("30/05/2018 10:00", "dd/MM/yyyy hh:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }






        Log.e("JsonObjectTest", singleQuestionJsonObj.toString());



        HashMap<String, String> params = new HashMap<>();
        params.put("mockJson", singleQuestionJsonObj.toString());


        try {
            DateUtilities.StringTimeToDate("30/05/2018 10:20", "dd/MM/yyyy hh:mm");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SendMockData(params);*/

        String question = generalQuestionObj.getQuestion();
        ArrayList<GeneralQuestionOptionObj> optionList = generalQuestionObj.getOptionList();

        for(int i=0;i<optionList.size();i++){
            GeneralQuestionOptionObj generalQuestionObj1 = optionList.get(i);
            Log.e("Options", i + " " +generalQuestionObj1.getOptionText() + " " +generalQuestionObj1.isOptionTheCorrectAnswer() + " " +generalQuestionObj1.getOptionPriorityInPercentage());
            JSONObject option1Obj = new JSONObject();
            option1Obj.put("optionname", generalQuestionObj1.getOptionText() );
            option1Obj.put("iscorrect", generalQuestionObj1.isOptionTheCorrectAnswer() );
            option1Obj.put("priority", generalQuestionObj1.getOptionPriorityInPercentage() );
            optionsJsonArray.put(option1Obj);

        }





        singleQuestionJsonObj = new JSONObject();
        singleQuestionJsonObj.put("Question", Question);
        singleQuestionJsonObj.put("optionList", optionsJsonArray);
        try {
            singleQuestionJsonObj.put("startDate", DateUtilities.StringTimeToDate(String.valueOf(generalQuestionObj.getStartDateStr()), "dd/MM/yyyy hh:mm"));
            singleQuestionJsonObj.put("endDate", DateUtilities.StringTimeToDate(String.valueOf(generalQuestionObj.getEndDateStr()), "dd/MM/yyyy hh:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("questionList","question lis t is " + question + " " + optionList.size());
        HashMap<String, String> params = new HashMap<>();
        params.put("mockJson", singleQuestionJsonObj.toString());
        SendMockData(params);



    }

    private void SendMockData(final HashMap<String, String> params){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/AddGeneralQuestion.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                Log.e("message", ""+response.toString());

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
    public void optionCorrectPriorityRemaining(String reason) {
        Snackbar.make(addBtn, reason, Snackbar.LENGTH_LONG)
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
    public void priorityPercentageGreater(String reason, float perRemaining) {
        Snackbar.make(addBtn, reason, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void questionCompleted() {
        Log.e("Question", "Adding Question to Server Database ");
        try {
            assembleSingleQuestionJsonObj(generalQuestionObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void questionNotCompleted(String reason) {
        Log.e("Question", reason);
        Snackbar.make(addBtn, reason, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
