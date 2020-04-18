package cn.pang.winemusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.pang.winemusic.R;
import cn.pang.winemusic.models.Song;

public class KSongListAdapter extends RecyclerView.Adapter<KSongListAdapter.KSongListHolder> {

    private static final String TAG = "RecyclerAdapter";
    public Context context;

    public List<Song> dataList = new ArrayList<>();

    public Song mData;

    public KSongListAdapter(Context context, List<Song> dataList) {
//        this.context = context;
        this.dataList = dataList;
    }

    /**
     * 创建KSongListHolder
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public KSongListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ksong_list_item, viewGroup, false);
        Log.e(TAG, "onCreateViewHolder");
        return new KSongListHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull KSongListAdapter.KSongListHolder kSongListHolder, int i) {
        //设置 Holder 数据
        kSongListHolder.kSongListItemName.setText(dataList.get(i).getName());
        kSongListHolder.kSongListItemRemark.setText(dataList.get(i).getRemark());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class KSongListHolder extends RecyclerView.ViewHolder {

        TextView kSongListItemName;
        TextView kSongListItemRemark;

        public KSongListHolder(View itemView) {
            super(itemView);
            this.kSongListItemName = itemView.findViewById(R.id.k_song_list_item_name);
            this.kSongListItemRemark = itemView.findViewById(R.id.k_song_list_item_remark);
        }
    }


}
