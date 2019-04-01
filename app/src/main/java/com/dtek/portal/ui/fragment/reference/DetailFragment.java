package com.dtek.portal.ui.fragment.reference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.itsm.ItService;
import com.dtek.portal.models.reference.Param;
import com.dtek.portal.ui.adapter.reference.ReferenceParamAdapter;
import com.dtek.portal.ui.fragment.itsm.DetailItsmFragment;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.References.REFERENCE_FILE_GET_DETAIL;
import static com.dtek.portal.Const.References.REFERENCE_URL_GET_DETAIL;

public class DetailFragment extends DetailItsmFragment {

    @BindView(R.id.layout_organization)
    LinearLayout layoutOrganization;
    @BindView(R.id.tv_name_organization)
    TextView tvNameOrganization;
    @BindView(R.id.layout_personnel_number)
    LinearLayout layoutPersonnelNumber;
    @BindView(R.id.tv_personnel_number)
    TextView tvPersonnelNumber;
    @BindView(R.id.layout_phone_number)
    LinearLayout layoutPhoneNumber;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.layout_params)
    LinearLayout layoutParams;
    @BindView(R.id.rv_reference_params)
    RecyclerView rvReferenceParams;
    @BindView(R.id.layout_reference_type)
    LinearLayout layoutReferenceType;
    @BindView(R.id.tv_reference_type)
    TextView tvReferenceType;
    @BindView(R.id.layout_subject)
    LinearLayout layoutSubject;


    public static DetailFragment newInstance(ItService itService) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_IT_SERVICE, itService);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @NonNull
    protected Map<String, String> getMapForOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, REFERENCE_URL_GET_DETAIL + itService.getIdOrder());
        map.put(ITSM_KEY_RESPONSE, REFERENCE_FILE_GET_DETAIL);
        return map;
    }

    @Override
    protected void initView(){
        layoutSubject.setVisibility(View.GONE);

        layoutOrganization.setVisibility(View.VISIBLE);
        layoutPersonnelNumber.setVisibility(View.VISIBLE);
        layoutPhoneNumber.setVisibility(View.VISIBLE);
        layoutReferenceType.setVisibility(View.VISIBLE);

        tvNameOrganization.setText(itDetail.getRoot().getOrganization());
        tvPersonnelNumber.setText(itDetail.getRoot().getTabNum());
        tvPhoneNumber.setText(itDetail.getRoot().getPhoneNumber());
        tvReferenceType.setText(itDetail.getRoot().getSubject());

        initParams(itDetail.getRoot().getParams());
    }

    private void initParams(List<Param> params){
        layoutParams.setVisibility(View.VISIBLE);
        params.remove(params.size()-1);
        params.remove(params.size()-1);
        ReferenceParamAdapter referenceAttributeAdapter = new ReferenceParamAdapter();
        rvReferenceParams.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReferenceParams.setAdapter(referenceAttributeAdapter);
        referenceAttributeAdapter.setItems(params);
    }
}
