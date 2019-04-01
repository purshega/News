package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dtek.portal.utils.PreferenceUtils;

import java.util.Map;

import static com.dtek.portal.Const.HTTP.*;

public class ListTwoLineInWorkFragment extends ListTwoLineFragment {

    @NonNull
    @Override
    public Map<String, String> getHeaders() {
        return mapHeader(
                ITSM_URL_IN_WORK + PreferenceUtils.getLogin(),
                null,
                ITSM_FILE_IN_WORK,
                null);
    }
}
