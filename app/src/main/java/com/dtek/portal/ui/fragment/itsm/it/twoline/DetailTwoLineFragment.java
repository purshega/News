package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.itsm.twoline.ItRootDetail;
import com.dtek.portal.ui.activity.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailTwoLineFragment extends Base2LineFragment {

    private static final String TAG = "DetailTwoLineFragment";
    private static final String ARG_IT_TASK = "it_two_line";
    private static final int REQUEST_DATA = 1; // константа для кода запроса

    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_date_start)
    TextView mTvDateStart;
    @BindView(R.id.tv_date_plan)
    TextView mTvDatePlan;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_initiator)
    TextView mTvInitiator;
    @BindView(R.id.tv_name_affects)
    TextView mTvNameAffects;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_room)
    TextView mTvRoom;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_priority)
    TextView mTvPriority;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.tv_turn)
    TextView mTvTurn;
    @BindView(R.id.tv_subject)
    TextView mTvSubject;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.fab_it_change)
    FloatingActionButton mFabItChange;
    ItRootDetail.TaskDetail taskDetail;


    public static DetailTwoLineFragment newInstance(ItRootDetail.TaskDetail taskDetail) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_IT_TASK, taskDetail);

        DetailTwoLineFragment fragment = new DetailTwoLineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailTwoLineFragment() {
        // Required empty public constructor
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            taskDetail = bundle.getParcelable(ARG_IT_TASK);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_it_two_line_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        fillView();
        return v;
    }

    private void fillView() {
        mTvNumber.setText(String.format("%s %s", taskDetail.getTypeOrder(), taskDetail.getIdOrder()));
        mTvDateStart.setText(taskDetail.getCreateDate());
        mTvDatePlan.setText(taskDetail.getPlanDate());
        mTvStatus.setText(taskDetail.getStatus());
        mTvInitiator.setText(taskDetail.getAuthor());
        mTvNameAffects.setText(taskDetail.getUser());
        mTvCity.setText(taskDetail.getCity());
        mTvAddress.setText(taskDetail.getAddress());
        mTvRoom.setText(taskDetail.getRoom());
        mTvPhone.setText(taskDetail.getPhone().startsWith("380") ?
                String.format("+%s", taskDetail.getPhone()) : taskDetail.getPhone());
        mTvEmail.setText(taskDetail.getEmail());
        mTvPriority.setText(taskDetail.getPriority());
        mTvService.setText(taskDetail.getService());
        mTvTurn.setText(taskDetail.getCommodity());
        mTvSubject.setText(taskDetail.getSubject());
        mTvDescription.setText(taskDetail.getDetails());
    }

    @OnClick(R.id.fab_it_change)
    public void onFabClick() {
        ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(StatusWorkFragment
                .newInstance(taskDetail), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
        }
    }
}
