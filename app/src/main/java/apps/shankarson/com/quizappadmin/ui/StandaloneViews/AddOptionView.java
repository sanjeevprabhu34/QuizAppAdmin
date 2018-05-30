package apps.shankarson.com.quizappadmin.ui.StandaloneViews;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionObj;
import apps.shankarson.com.quizappadmin.model.GeneralQuestionOptionObj;

public class AddOptionView {
    public AddOptionView(final GeneralQuestionOptionObj generalQuestionOptionObj, final ViewGroup parentVg){
        Context context = parentVg.getContext();
      //  String OptionQuestion = generalQuestionObj.getQuestion();

        LayoutInflater inflater = LayoutInflater.from(context);
        final View row = inflater.inflate(R.layout.options_single_row, parentVg, false);
        parentVg.addView(row);

        ImageView iv = row.findViewById(R.id.option_delete);
        TextView optionTextTv = row.findViewById(R.id.option_name_tv);



        optionTextTv.setText(generalQuestionOptionObj.getOptionText());

        boolean isOptionCorrect = generalQuestionOptionObj.isOptionTheCorrectAnswer();

        if(isOptionCorrect){
            row.setBackgroundColor(Color.GREEN);
        }else{
            row.setBackgroundColor(Color.RED);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("deleting", "deleting");
                parentVg.removeView(row);
            }
        });


    }
}
