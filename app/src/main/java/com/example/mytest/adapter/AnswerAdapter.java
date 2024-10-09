package com.example.mytest.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.R;
import com.example.mytest.model.Answer;
import com.example.mytest.repository.AnswerRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private final List<Answer> answerList;
    private final Context context;
    public AnswerAdapter(List<Answer> answerList, Context context) {
        this.answerList = answerList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_item_list, parent, false);
        return new AnswerAdapter.AnswerViewHolder(view, this, answerList);
    }
    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AnswerViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.bind(answer);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
    static class AnswerViewHolder extends RecyclerView.ViewHolder{
        private final TextView text;
        private final AnswerRepository answerRepository;
        private final AnswerAdapter answerAdapter;
        private final List<Answer> answerList;

        public AnswerViewHolder(@NonNull View itemView, AnswerAdapter answerAdapter, List<Answer> answerList) {
            super(itemView);
            text = itemView.findViewById(R.id.answer_item);
            this.answerAdapter = answerAdapter;
            this.answerList = answerList;
            answerRepository = new AnswerRepository(FirebaseFirestore.getInstance());
        }

        public void bind(Answer answer) {
            text.setText(answer.getContent());

            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_answer, null);

                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);
                    CheckBox checkBox = dialogView.findViewById(R.id.dialog_answer_checkBox);

                    editText.setText(answer.getContent());
                    checkBox.setChecked(answer.isCorrect());

                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("Отредактируйте ответ")
                            .setView(dialogView)
                            .setPositiveButton("Обновить", (dialog1, which) -> {

                                answer.setContent(editText.getText().toString());
                                answer.setCorrect(checkBox.isChecked());

                                answerRepository.updateAnswer(answer);
                                answerAdapter.notifyItemChanged(getAdapterPosition());
                            })
                            .setNegativeButton("Удалить", (dialog12, which) -> {

                                answerList.remove(getAdapterPosition());
                                answerRepository.deleteAnswer(answer);
                                answerAdapter.notifyItemRemoved(getAdapterPosition());
                            })
                            .create();

                    dialog.show();
                }
            });
        }
    }
}
