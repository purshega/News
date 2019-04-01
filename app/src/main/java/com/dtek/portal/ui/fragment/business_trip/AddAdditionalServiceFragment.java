package com.dtek.portal.ui.fragment.business_trip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BT;
import com.dtek.portal.models.business_trips.BTAdditionalService;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.BusinessTrip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_ADDITIONAL_SERVICES;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class AddAdditionalServiceFragment extends Fragment {

    private BT businessTrip;
    private BTAdditionalService btAdditionalService;
    private Boolean newAdditionalService = true;

    private Unbinder unbinder;
    private Dialog waitDialog;

    @BindView(R.id.spinner_services)
    Spinner spinnerServices;
    @BindView(R.id.et_comments)
    EditText etComment;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    public AddAdditionalServiceFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddAdditionalServiceFragment(BTAdditionalService btAdditionalService) {
        this.btAdditionalService = btAdditionalService;
        newAdditionalService = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_additional_service_add, container, false);
        unbinder = ButterKnife.bind(this, v);
        businessTrip = BusinessTrip.getInstance().getBusinessTrip();

        initDialog();
        initDeleteButton();
        initService();
        getServices();
        return v;
    }

    private void initService(){
        if(null == btAdditionalService)
            btAdditionalService = new BTAdditionalService();
        else
            loadAdditionalServiceData();
    }

    private void loadAdditionalServiceData(){
        btnDelete.setVisibility(View.VISIBLE);
        etComment.setText(btAdditionalService.getComment());
    }

    @OnClick({R.id.btn_save, R.id.btn_delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveAdditionalService();
                break;
            case  R.id.btn_delete:
                deleteAdditionalService();
                break;
        }
    }


    private void getServices() {
            waitDialog.show();

            Map<String, String> map = new HashMap<>();
            map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

            RestManager.getApi()
                    .getAdditionalServices(map, API_BT + BT_GET_ADDITIONAL_SERVICES)
                    .enqueue(new Callback<List<BTAdditionalService.AdditionalService>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<BTAdditionalService.AdditionalService>> call, @NonNull Response<List<BTAdditionalService.AdditionalService>> response) {
                            RestManager.printResponseLog(response);
                            switch (response.code()) {
                                case 200:
                                    if (response.isSuccessful() && response.body() != null) {
                                        List<BTAdditionalService.AdditionalService> btAdditionalServices = response.body();
                                        initSpinnerServices(btAdditionalServices);
                                    }
                                    break;
                                case 400:
                                case 401:
                                    ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                    break;
                            }
                            waitDialog.dismiss();
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<BTAdditionalService.AdditionalService>> call, Throwable t) {
                            t.printStackTrace();
                            waitDialog.dismiss();
                        }
                    });
    }

    private void initSpinnerServices(List<BTAdditionalService.AdditionalService> btAdditionalServices){
        ArrayAdapter<BTAdditionalService.AdditionalService> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, btAdditionalServices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(adapter);
        spinnerServices.setSelection(getIndexService(btAdditionalServices));
        initSpinnerServicesListener(btAdditionalServices);
    }

    private int getIndexService(List<BTAdditionalService.AdditionalService> btAdditionalServices){
        if(btAdditionalService.getAdditionalService()!=null) {
            for (int i = 0; i < btAdditionalServices.size(); i++) {
                if (btAdditionalServices.get(i).getId().equals(btAdditionalService.getAdditionalService().getId()))
                    return i;
            }
        }
        return 0;
    }

    private void initSpinnerServicesListener(List<BTAdditionalService.AdditionalService> tripTypesList) {
        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btAdditionalService.setAdditionalService(tripTypesList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(getContext());
    }

    private void initDeleteButton(){
        if(newAdditionalService)
            btnDelete.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void saveAdditionalService(){
        btAdditionalService.setComment(etComment.getText().toString());
        if(newAdditionalService)
        businessTrip.getAdditionalServices().add(btAdditionalService);
        (Objects.requireNonNull(getActivity())).onBackPressed();
    }

    private void deleteAdditionalService(){
        showDeleteDialog();
    }

    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Так", (dialog, which) -> {
            businessTrip.getAdditionalServices().remove(btAdditionalService);
            (Objects.requireNonNull(getActivity())).onBackPressed();
        });
        builder.setNegativeButton("Ні", (dialog, which) -> {
        });
        builder.setMessage(Objects.requireNonNull(getContext()).getString(R.string.text_service_delete));
        AlertDialog alert = builder.create();
        alert.show();
    }

}