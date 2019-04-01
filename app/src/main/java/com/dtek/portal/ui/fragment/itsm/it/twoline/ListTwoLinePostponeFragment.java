package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.support.annotation.NonNull;

import com.dtek.portal.utils.PreferenceUtils;

import java.util.Map;

import static com.dtek.portal.Const.HTTP.*;

public class ListTwoLinePostponeFragment extends ListTwoLineFragment {

    @NonNull
    @Override
    public Map<String, String> getHeaders() {
        return mapHeader(
                ITSM_URL_POSTPONE + PreferenceUtils.getLogin(),
                null,
                ITSM_FILE_POSTPONE,
                null);
    }
}
