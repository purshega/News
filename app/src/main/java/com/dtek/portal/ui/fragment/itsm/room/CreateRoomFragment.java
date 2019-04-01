package com.dtek.portal.ui.fragment.itsm.room;

import android.support.annotation.NonNull;

import com.dtek.portal.ui.fragment.itsm.CreateItsmFragment;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dtek.portal.Const.HTTP.*;

public class CreateRoomFragment extends CreateItsmFragment {

    @NonNull
    @Override
    protected Map<String, String> fillMapHeader() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_CREATE);
        map.put(ITSM_KEY_REQUEST, ROOMS_FILE_CREATE);
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_CREATE_RESP);
        map.put(ITSM_KEY_CONTENT_TYPE, ITSM_CONTENT_TYPE_SOAP);
        return map;
    }
}
