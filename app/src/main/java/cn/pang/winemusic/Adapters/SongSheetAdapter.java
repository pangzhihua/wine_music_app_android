package cn.pang.winemusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.pang.winemusic.R;
import cn.pang.winemusic.models.SongSheet;

public class SongSheetAdapter extends ArrayAdapter<SongSheet> {

    private int resourceId;

    //将上下文、ListView子项布局的id、数据 传递进来
    public SongSheetAdapter(Context context, int textViewResourceId, List<SongSheet> obj) {
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SongSheet songSheet = getItem(position);//获取当前项的Weather实例
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //获取实例
        ImageView image = (ImageView) view.findViewById(R.id.song_sheet_image);
        TextView name = (TextView) view.findViewById(R.id.song_sheet_name);
        TextView num = (TextView) view.findViewById(R.id.song_sheet_number);
        //设置图片和文字
        image.setImageResource(songSheet.getImageId());
        name.setText(songSheet.getName());
        num.setText(songSheet.getNumber() + "首");
        return view;
    }
}
