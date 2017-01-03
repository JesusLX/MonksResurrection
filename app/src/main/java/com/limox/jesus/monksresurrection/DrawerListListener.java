package com.limox.jesus.monksresurrection;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.limox.jesus.monksresurrection.Repositories.Users_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

/**
 * Created by jesus on 3/01/17.
 */

public class DrawerListListener implements ListView.OnItemClickListener{

    private onDrawerListListenerCallbacks mCallback;

    public DrawerListListener(Context context) {
        if (context instanceof onDrawerListListenerCallbacks)
            this.mCallback = (onDrawerListListenerCallbacks) context;
        else
            throw new ClassCastException(context.toString()+" must implement onDrawerListListenerCallbacks");
    }

    public interface onDrawerListListenerCallbacks{
        void startUserProfile(Bundle user);
        void startBugForum();
        void startAdminZone();
        void startSettings();
        void startHelp();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position){
            case 0:
                Bundle user = new Bundle();
                user.putParcelable(AllConstants.USER_PARCELABLE_KEY, Users_Repository.get().getCurrentUser());
                mCallback.startUserProfile(user);
                break;
            case 1:
                mCallback.startBugForum();
                break;
            case 2:
                mCallback.startAdminZone();
                break;
            case 3:
                mCallback.startSettings();
                break;
            case 4:
                mCallback.startHelp();
                break;
        }

    }
}
