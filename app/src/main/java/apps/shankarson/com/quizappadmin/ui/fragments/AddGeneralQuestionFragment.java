package apps.shankarson.com.quizappadmin.ui.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import apps.shankarson.com.quizappadmin.AppConstants.DateTimeConstants;
import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.Utilities.DateUtilities;
import apps.shankarson.com.quizappadmin.Utilities.StringUtilities;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionOptionObj;
import apps.shankarson.com.quizappadmin.ui.StandaloneViews.AddOptionView;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionObj;
import apps.shankarson.com.quizappadmin.model.User;
import apps.shankarson.com.quizappadmin.ui.StandaloneViews.ProgressBar;
import apps.shankarson.com.quizappadmin.ui.adapters.ViewUsersAdapter;

public class AddGeneralQuestionFragment extends Fragment implements GeneralQuestionObj.Interactor, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private GeneralQuestionObj generalQuestionObj;
    private TextView percentageRemainignTv;
    private EditText optionPriorityPercentageTv;
    private EditText questionEt ;
    private EditText startTimeEt;
    private EditText endTimeEt ;
    private ViewGroup optionsContainer;
    private EditText optionText;
    private boolean isCorrectOption = false;
    private SwitchCompat correctAnswerSwitch;
    private Button doneBtn;
    private FloatingActionButton addBtn;
    private JSONObject singleQuestionJsonObj;
    private ProgressBar progressBarFactory;
    private ProgressDialog progressDialog;
    private Button startDateBtn, endDateBtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private android.app.DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText targetDateEt;
    private String dateStrSelected;
    private String timeStrSelected = "00:00";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_general_question, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

       /* String timeStr = "26/05/2018 14:20";
        String format = DateTimeConstants.DATE_FORMAT;

        try {
            DateUtilities.StringTimeToDate(timeStr, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    private void init() {
        questionEt = getActivity().findViewById(R.id.questionEt);
        startTimeEt = getActivity().findViewById(R.id.startDateEt);
        endTimeEt = getActivity().findViewById(R.id.endDateEt);
        optionsContainer = getActivity().findViewById(R.id.options_container);
        optionText = getActivity().findViewById(R.id.optionTextEt);
        startDateBtn = getActivity().findViewById(R.id.startDateBtn);
        endDateBtn = getActivity().findViewById(R.id.endDateBtn);

        percentageRemainignTv = getActivity().findViewById(R.id.optionPercentageRemaining);
        optionPriorityPercentageTv = getActivity().findViewById(R.id.optionPriority);
        correctAnswerSwitch = getActivity().findViewById(R.id.optionSwitch);
        doneBtn = getActivity().findViewById(R.id.done_btn);
        progressBarFactory = ProgressBar.getInstance();

        java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

        createDateDialog();
        createtimeDialog();


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
               /* long startTime = 0;
                long endTime = 0;
                try {
                    startTime = DateUtilities.StringTimeToDate(startTimeEt.getText().toString(), "dd/mm/yyyy");
                    endTime = DateUtilities.StringTimeToDate(startTimeEt.getText().toString(), "dd/mm/yyyy");
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Log.e("editing", "start time is "  +generalQuestionObj.getStartDateStr());


                */
               String optionPercentageText = optionPriorityPercentageTv.getText().toString();
               if(optionPercentageText.length() == 0){
                   optionPercentageText = "0";
               }

                generalQuestionObj.checkForOptionCompleted(optionTextStr, isCorrectOption, Float.parseFloat(optionPercentageText));
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

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetDateEt = startTimeEt;
                datePickerDialog.show();

            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetDateEt = endTimeEt;
                datePickerDialog.show();

            }
        });



        //assembleSingleQuestionJsonObj(generalQuestionObj);
    }

    private void createDateDialog(){

        datePickerDialog = new DatePickerDialog(
                getActivity(), this, 2018, 06, 04);



    }

    private void createtimeDialog(){

        timePickerDialog = new TimePickerDialog(
                getActivity(), this, 00, 00, true);



    }

    /*private void setTimeDialog(Button targetBtn){
        final java.util.Calendar c = java.util.Calendar.getInstance();
            mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
            mMinute = c.get(java.util.Calendar.MINUTE);

            // Launch Time Picker Dialog
            android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),
                    new android.app.TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endTimeEt.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
    }*/

    private void resetAllOptionFields(){
        optionText.setText(null);
        correctAnswerSwitch.setChecked(false);
    }

    private void assembleSingleQuestionJsonObj(GeneralQuestionObj generalQuestionObj) throws JSONException {
        String Question = generalQuestionObj.getQuestion();
        JSONArray optionsJsonArray = new JSONArray();
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

            singleQuestionJsonObj.put("startDate",generalQuestionObj.getStartDateStr());
            singleQuestionJsonObj.put("endDate", generalQuestionObj.getEndDateStr());


        Log.e("questionList","question lis t is " + question + " " + optionList.size());
        HashMap<String, String> params = new HashMap<>();
        params.put("mockJson", singleQuestionJsonObj.toString());

       // progressDialog = progressBarFactory.createNewProgressDialog(getActivity());

        //progressDialog.show();

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
               // Log.e("message", ""+response.toString());
                singleQuestionSetCompletedSuccessfully(response);

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

    private void singleQuestionSetCompletedSuccessfully(String serverResponse){
        //progressDialog.cancel();
        resetAllToSart();
    }

    private void resetAllToSart(){
        questionEt.setText(null);
        optionText.setText(null);
        correctAnswerSwitch.setChecked(false);
        optionsContainer.removeAllViews();
        optionPriorityPercentageTv.setText("");
        percentageRemainignTv.setText(String.valueOf(100));
        
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
    public void optionTextComplete(GeneralQuestionOptionObj generalQuestionOptionObj, float perRemaining) {
        Log.e("options", "Correctlt entered");
        AddOptionView addOption = new AddOptionView(generalQuestionOptionObj,optionsContainer);
        priorityPercentageRemaining(perRemaining);
        resetAllOptionFields();
    }

    @Override
    public void priorityPercentageRemaining(float perRemaining) {
        percentageRemainignTv.setText(String.valueOf(100-perRemaining));
        optionPriorityPercentageTv.setText(String.valueOf(perRemaining));

    }

    @Override
    public void priorityPercentageGreater(String reason, float perRemaining) {
        Snackbar.make(addBtn, reason, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        priorityPercentageRemaining(perRemaining);
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

    @Override
    public void startTimeNotCorrect() {
        Snackbar.make(addBtn, "Please set Start time", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void endTimeNotCorrect() {
        Snackbar.make(addBtn, "Please set end time", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void startDateSet(long timeinMilliseconds) {
        String dateStr = DateUtilities.UTCMilliseondsToDate(timeinMilliseconds);
      // startTimeEt.setText(dateStr);

        Log.e("startDate", "StartDate set to " + timeinMilliseconds);
    }

    @Override
    public void endDateSet(long timeinMilliseconds) {
        //endTimeEt.setText(DateUtilities.UTCMilliseondsToDate(timeinMilliseconds));
    }

    //8433936080 - Seema
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String day = StringUtilities.AddPrecedingZeroIfSingleDigit(dayOfMonth);
        String mnth =StringUtilities.AddPrecedingZeroIfSingleDigit(month+1);
        dateStrSelected = day + "/" + mnth + "/"+year;

        datePickerDialog.hide();
        timePickerDialog.show();


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeStrSelected = StringUtilities.AddPrecedingZeroIfSingleDigit(hourOfDay) + ":" + StringUtilities.AddPrecedingZeroIfSingleDigit(minute);
        dateTimeSelectedByUser();


    }

    private void dateTimeSelectedByUser() {
        // targetDateEt.setText(day + "/" + mnth + "/"+year);
        StringBuilder dateTimeBuilder = new StringBuilder();
        dateTimeBuilder.append(dateStrSelected);
        dateTimeBuilder.append(" ");
        dateTimeBuilder.append(timeStrSelected);
        targetDateEt.setText(dateTimeBuilder.toString());


            try {
           if(targetDateEt.getId() == startTimeEt.getId()){
                generalQuestionObj.setStartDateStr(DateUtilities.StringTimeToDate(dateTimeBuilder.toString(),DateTimeConstants.DATE_FORMAT ));
            }else if(targetDateEt.getId() == endTimeEt.getId()){
               generalQuestionObj.setEndDateStr(DateUtilities.StringTimeToDate(dateTimeBuilder.toString(),DateTimeConstants.DATE_FORMAT ));
              }
            } catch (ParseException e) {
                e.printStackTrace();
            }


    }


}
