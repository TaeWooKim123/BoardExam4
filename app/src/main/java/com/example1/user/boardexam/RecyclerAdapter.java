package com.example1.user.boardexam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Board> listData = new ArrayList<>();
    Context context;


    RecyclerAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Board board) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(board);
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
        }

        void onBind(Board data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            //String file = data.getFilename();
            //System.out.printf("태우킴 : %s", file);
            imagebind(data);
            //imageView.setImageResource(data.getResId());
        }

        void imagebind(Board data) {
            //FirebaseStorage fs = FirebaseStorage.getInstance();
            //StorageReference imageRef = fs.getReference().child("image/" + data.filename);
            String filename_final = "gs://testfirebase-b3ec0.appspot.com/image/".concat(data.filename);
            System.out.printf("태우킴의 %s", filename_final);
            Picasso.with(context).load(filename_final).fit().centerInside().into(imageView);
        }


        //Glide.with().load(imageRef).into(binding.)
    }

}
