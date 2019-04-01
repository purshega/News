package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.support.annotation.NonNull;

import com.dtek.portal.utils.PreferenceUtils;

import java.util.Map;

import static com.dtek.portal.Const.HTTP.ITSM_FILE_NEW;
import static com.dtek.portal.Const.HTTP.ITSM_URL_NEW;

public class ListTwoLineNewsFragment extends ListTwoLineFragment {

    @NonNull
    @Override
    public Map<String, String> getHeaders() {
        return mapHeader(
                ITSM_URL_NEW + PreferenceUtils.getLogin(),
                null,
                ITSM_FILE_NEW,
                null);
    }
}
