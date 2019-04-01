package com.dtek.portal.ui.fragment.itsm.room;

import android.support.annotation.NonNull;

import com.dtek.portal.ui.fragment.itsm.ItsmAllFragment;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dtek.portal.Const.HTTP.*;

public class RoomAllFragment extends ItsmAllFragment {

    @NonNull
    @Override
    protected Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ROOMS_URL_ALL + PreferenceUtils.getLogin());
        map.put(ITSM_KEY_RESPONSE, ROOMS_FILE_ALL);
        return map;
    }
}
