package com.dtek.portal.ui.fragment.newspaper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.models.newspaper.Newspaper;
import com.dtek.portal.ui.activity.NewsPaperActivity;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.ui.fragment.newspaper.adapter.ArchiveNewPaperAdapter;
import com.dtek.portal.utils.NewspaperUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArchiveNewsPaperFragment extends Fragment implements ArchiveNewPaperAdapter.NewsPaperListener {

    private static final String ARG_NEWSPAPER_LIST = "newspaper_list";

    private Unbinder unbinder;
    private Context mContext;
    private Dialog waitDialog;
    private List<Newspaper> newspaper_list;

    private ArchiveNewPaperAdapter archiveNewPaperAdapter;

    @BindView(R.id.newspaper_recycler_view)
    RecyclerView newspaperRV;


    public static ArchiveNewsPaperFragment newInstance(List<Newspaper> newspaper) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NEWSPAPER_LIST, (ArrayList<? extends Parcelable>) newspaper);
        ArchiveNewsPaperFragment fragment = new ArchiveNewsPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        initDialog();
//        waitDialog.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newspaper_archive, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (getArguments() != null) {
            newspaper_list = getArguments().getParcelableArrayList(ARG_NEWSPAPER_LIST);
            initRecyclerView();
            loadNewsPaperList();
        }
        return v;
    }

    private void initRecyclerView(){
        newspaperRV.setLayoutManager(new LinearLayoutManager(mContext));
        archiveNewPaperAdapter = new ArchiveNewPaperAdapter();
        archiveNewPaperAdapter.setNewsPaperListener(this);
        newspaperRV.setAdapter(archiveNewPaperAdapter);
    }

    private void loadNewsPaperList(){
        archiveNewPaperAdapter.setItems(newspaper_list);
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(Newspaper newsPaper) {
        if (getActivity() != null) {
            NewspaperUtils.deletePdfFileInStorage(mContext, newsPaper.getNewspaper_name()+".pdf");
            Intent intent = new Intent(getActivity(), NewsPaperActivity.class);
            intent.putExtra(Const.HTTP.NEWSPAPER_URL, newsPaper.getNewspaper_url());
            intent.putExtra(Const.PDF_NEWSPAPER.NEWSPAPER_NAME, newsPaper.getNewspaper_name()+".pdf");
            getActivity().startActivity(intent);
        }
    }
}
