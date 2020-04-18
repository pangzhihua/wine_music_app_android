package cn.pang.winemusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.pang.winemusic.R;
import cn.pang.winemusic.models.Song;

public class SongAdapter extends ArrayAdapter<Song> {

    private int resourceId;
    private int resourceId2;
    private Integer isIndex;
    private List<Song> obj;

    //将上下文、ListView子项布局的id、数据 传递进来
    public SongAdapter(Context context, int textViewResourceId, List<Song> obj) {
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }

    public SongAdapter(Context context, int textViewResourceId, int textViewResourceId2, List<Song> obj, int isIndex) {
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
        resourceId2 = textViewResourceId2;
        this.isIndex = isIndex;
        this.obj = obj;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Song song = getItem(position);//获取当前项的Weather实例
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        View view;
        if (position != isIndex) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            //获取实例
            TextView name = view.findViewById(R.id.player_bottom_list_item_name);
            //设置图片和文字
            name.setText(song.getName());
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId2, parent, false);
            TextView name = view.findViewById(R.id.player_bottom_list_item2_name);
            //设置图片和文字
            name.setText(song.getName());
        }

        return view;
    }
}
