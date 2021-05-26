package be.bxl.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.bxl.todolist.R;
import be.bxl.todolist.db.TaskDAO;
import be.bxl.todolist.models.Task;
import be.bxl.todolist.utils.Convert;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> tasks;
    Context context;
    ListItemClickListener listItemClickListener;

    public TaskAdapter(Context context, ListItemClickListener listItemClickListener, ArrayList<Task> tasks) {
        this.context = context;
        this.listItemClickListener = listItemClickListener;
        this.tasks = tasks;
    }

    public interface ListItemClickListener {
        void onBtnItemClickListener(long taskId);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPriority, tvDate, btnCheck;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvName = v.findViewById(R.id.tv_item_task);
            tvPriority = v.findViewById(R.id.tv_item_priority);
            tvDate = v.findViewById(R.id.tv_item_date);
            btnCheck = v.findViewById(R.id.btn_item_add);

        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvPriority() {
            return tvPriority;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getBtnCheck() {
            return btnCheck;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View taskView = layoutInflater.inflate(R.layout.item_task, parent, false);

        return new ViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.getTvName().setText(task.getName());
        holder.getTvPriority().setText(Convert.convertPriorityToString(context, task.getPriority()));
        if ((holder.getTvPriority().getText().toString()).contentEquals(context.getText(R.string.priority_high_string))) {
            holder.getTvPriority().setTextColor(context.getColor(R.color.red));
        } else {
            holder.getTvPriority().setTextColor(context.getColor(R.color.black));
        }
        holder.getTvDate().setText(task.getDate());

        holder.getBtnCheck().setOnClickListener(v -> {
            listItemClickListener.onBtnItemClickListener(task.getId());
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
