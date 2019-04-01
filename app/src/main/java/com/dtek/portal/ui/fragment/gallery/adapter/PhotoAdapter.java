package com.dtek.portal.ui.fragment.gallery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtek.portal.R;
import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.utils.PicassoSingleton;
import com.dtek.portal.utils.Utils;
import com.squareup.picasso.MemoryPolicy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private static final String TAG = "GalleryAdapter";

    private List<Photo> mPhotoList = new ArrayList<>();

    public PhotoAdapter(List<Photo> list) {
        this.mPhotoList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        PhotoAdapter.ViewHolder vh = new PhotoAdapter.ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Photo photo = mPhotoList.get(position);
        Log.e(TAG, "bind, position = " + position);

        if (photo.getUrl() != null && !photo.getUrl().equals("") && Utils.isNetworkAvailable(holder.itemView.getContext())) {

//            String testLink = "http://www.mocky.io/v2/5b72e0c0320000a3303a7ea3";

            PicassoSingleton.getInstance(holder.ivPhoto.getContext())
                    .load(API_BASE_URL + NEWS_BASE64 + photo.getUrl() + "&maxwidth=800&maxheight=400")
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.progress_animation)
                    .fit().centerCrop()
                    .error(R.drawable.img_no_photo)
                    .into(holder.ivPhoto);

//            Picasso.get()
//                    .load(testLink)
//                    .placeholder(R.drawable.progress_animation)
//                    .error(R.drawable.img_no_photo)
//                    .into(holder.ivPhoto);


//            String encodeUrl = Uri.encode(photo.getUrl());
//            String url = API_BASE_URL + NEWS_BASE64 + encodeUrl;
//            String base64 = null;
//
//            Glide.with(holder.ivPhoto.getContext())
//                    .asBitmap()
////                .load(API_BASE_URL + NEWS_BASE64 + encodeUrl)
//                    .load(testLink)
//                    .into(holder.ivPhoto);


        }


        holder.itemView.setOnClickListener(v -> {
            if (sPhotoListener != null) {
//                sPhotoListener.onItemClick(photo);
                sPhotoListener.onItemClick(position);
            }
        });

    }


    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mPhotoList == null ? 0 : mPhotoList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private PhotoListener sPhotoListener; //переменная

    public void setPhotoListener(PhotoListener listener) {//кот. содержит в себе объект
        this.sPhotoListener = listener;
    }

    public interface PhotoListener { //этот объект реализует этот интерфейс
//        void onItemClick(Photo currentPhoto);
        void onItemClick(int i);
    }
}
