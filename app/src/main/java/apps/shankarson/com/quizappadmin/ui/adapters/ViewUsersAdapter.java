package apps.shankarson.com.quizappadmin.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apps.shankarson.com.quizappadmin.R;
import apps.shankarson.com.quizappadmin.model.User;

public class ViewUsersAdapter extends RecyclerView.Adapter<ViewUsersAdapter.MyViewHolder> {
    private ArrayList<User> userList;

    public ViewUsersAdapter(ArrayList<User> userList){
        this.userList = userList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_single_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = userList.get(position).getName();
        holder.nameTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public  TextView nameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);

        }
    }
}
