package com.dtek.portal.ui.fragment.reference;

import android.support.annotation.NonNull;

import com.dtek.portal.models.itsm.ItService;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.itsm.ItsmAllFragment;
import com.dtek.portal.utils.PreferenceUtils;


import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.References.REFERENCE_FILE_GET_ALL;
import static com.dtek.portal.Const.References.REFERENCE_URL_ALL;

public class ReferenceAllListFragment extends ItsmAllFragment {

    @NonNull
    protected Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, REFERENCE_URL_ALL + PreferenceUtils.getLogin());
        map.put(ITSM_KEY_RESPONSE, REFERENCE_FILE_GET_ALL);
        return map;
    }

    @Override
    public void onItemClick(ItService currentOrder) {
        if (getActivity() != null) {
//            ((MainActivity) getActivity()).switchToFragment(DetailReferenceFragment.newInstance(currentOrder), false);
            ((MainActivity) getActivity()).switchToFragment(DetailFragment.newInstance(currentOrder), false);
        }
    }
}