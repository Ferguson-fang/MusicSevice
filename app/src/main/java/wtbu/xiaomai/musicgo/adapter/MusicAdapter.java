package wtbu.xiaomai.musicgo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wtbu.xiaomai.musicgo.R;
import wtbu.xiaomai.musicgo.bean.MusicBean;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private List<MusicBean> list;
    //private OnItemClickListener mOnItemClickListener;

    public MusicAdapter(List<MusicBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MusicBean musicBean = list.get(position);
        holder.albumName.setText(musicBean.getAlbumName());
        holder.songName.setText(musicBean.getSongName());
        holder.singerName.setText(musicBean.getSingerName());

        /*holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View rootView;
        TextView songName;
        TextView singerName;
        TextView albumName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            songName = itemView.findViewById(R.id.item_local_music_song);
            singerName = itemView.findViewById(R.id.item_local_music_singer);
            albumName = itemView.findViewById(R.id.item_local_music_album);
        }
    }

    /*//内部接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }*/
}
