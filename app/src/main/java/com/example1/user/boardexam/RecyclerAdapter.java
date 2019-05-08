package com.example1.user.boardexam;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    ArrayList<Board> mBoard = new ArrayList<>();
    Context context;
    int temp_position;
    View view;
    RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        //temp_position = position;
        //Toast.makeText(view.getContext(), "태우짱!!!", Toast.LENGTH_SHORT).show(); //이 부분은 아님
        holder.onBind(mBoard.get(position));

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return mBoard.size();
    }

    void addItem(Board board) {
        // 외부에서 item을 추가시킬 함수입니다.
        mBoard.add(board);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Toast.makeText(v.getContext(), "태우짱!!!" + textView1.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Roominfo.class); //클릭시 DetailItem
                    intent.putExtra("TITLE", textView1.getText());
                    intent.putExtra("CONTENT", textView2.getText());
                    intent.putExtra("LIST", mBoard); //이때 같이 board를 넘겨준다!!!! 받는곳에서는 태우야 Intent intent = getIntent로 받으면 된다.
                    context.startActivity(intent);
                }
            });
        }

        void onBind(Board data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            String a = data.downloadURL;
            Picasso.with(context).load(a).fit().centerInside().into(imageView);
            //Toast.makeText(view.getContext(), "태우짱!!!", Toast.LENGTH_SHORT).show(); 이부분도 아님.
        }

        /*void imagebind(Board data) {
            FirebaseStorage fs = FirebaseStorage.getInstance();
            StorageReference imageRef = fs.getReference().child("image/" + data.filename);
            //String filename_final = "gs://testfirebase-b3ec0.appspot.com/image/".concat(data.filename);
            //System.out.printf("태우킴의 %s", filename_final);
            Picasso.with(context).load(imageRef.toString()).fit().centerInside().into(imageView);
        }*/
    }

}
