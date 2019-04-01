//package com.dtek.portal.ui.fragment.qr;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.dtek.portal.R;
//import com.dtek.portal.models.qr.QrResp;
//import com.dtek.portal.ui.activity.MainActivity;
//import com.dtek.portal.ui.adapter.QrTaskAdapter;
//import com.dtek.portal.ui.fragment.car.AddDetailCarFragment;
//import com.dtek.portal.ui.fragment.itsm.CreateItsmFragment;
//import com.dtek.portal.ui.fragment.itsm.room.CreateRoomFragment;
//
//import java.util.List;
//import java.util.Objects;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//
////public class QrListFragment extends Fragment implements QrTaskAdapter.TaskClickListener {
//public class QrListFragment extends Fragment {
//
//    public static final String ARG_QR_DATA = "qr_data";
//    public static final String ARG_QR_TASK = "qr_task";
//    public static final String ITSM = "ITSM";
//    public static final String AHO = "AHO";
//
//    @BindView(R.id.tv_empty_list)
//    TextView mTvEmptyList;
//    @BindView(R.id.main_recycler)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.main_progress)
//    ProgressBar mMainProgress;
//
//    private Unbinder unbinder;
//    private QrResp qrResp;
//    private List<String> mList;
//    private QrTaskAdapter mAdapter;
//
//    public QrListFragment() {
//        // Required empty public constructor
//    }
//
//    public static QrListFragment newInstance(QrResp qrResp) {
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_QR_DATA, qrResp);
//        QrListFragment fragment = new QrListFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        qrResp = getArguments() != null ? getArguments().getParcelable(ARG_QR_DATA) : null;
//        mList = qrResp != null ? qrResp.getInfoList() : null;
//    }
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.layout_list, container, false);
//        unbinder = ButterKnife.bind(this, v);
////        initAdapter();
//        initNextService();
//        return v;
//    }
//
////    private void initAdapter() { // создаем адаптер
////        mAdapter = new QrTaskAdapter(mList, this);
////        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        mRecyclerView.setAdapter(mAdapter);
////    }
//
////    @Override
////    public void onItemClick(String task) {
////        Toast.makeText(getContext(), task, Toast.LENGTH_SHORT).show();
////        switch (qrResp.getService()) {
////            case ITSM:
////                ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateItsmFragment.newInstance(qrResp, task));
////                break;
////            case AHO:
////                ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateRoomFragment.newInstance(qrResp, task));
////                break;
////        }
////
////    }
//
//    private void initNextService() {
//        switch (qrResp.getService()) {
//            case ITSM:
//                ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateItsmFragment.newInstance(qrResp));
//                break;
//            case AHO:
//                ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateRoomFragment.newInstance(qrResp));
//                break;
//        }
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//}
