package com.dtek.portal.ui.fragment.itsm.room;

import android.annotation.SuppressLint;

import com.dtek.portal.R;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.ui.fragment.itsm.TabItsmFragment;

@SuppressLint("ValidFragment")
public class TabRoomFragment extends TabItsmFragment {

    public TabRoomFragment(int currentTab) {
        super(currentTab);
    }

    @Override
    public void addChildFragment(BasePagerAdapter pagerAdapter) {
        pagerAdapter.addFragment(new RoomAllFragment(), getString(R.string.tab_title_all));
        pagerAdapter.addFragment(new RoomActiveFragment(), getString(R.string.tab_title_active));
        pagerAdapter.addFragment(new RoomArchiveFragment(), getString(R.string.tab_title_archive));
    }

    @Override
    protected void onClickFab() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(new CreateRoomFragment(), false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_room_service));
        }
    }
}
