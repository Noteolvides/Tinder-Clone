package com.example.tinder.Interfaces;

import com.example.tinder.Model.CardOfPeople;
import com.example.tinder.Model.Invite;

public interface DataCallback {

    //Profile update
    void onProfilePutSuccess();

    void onProfilePutFailed(String reason);

    //Own activity_profile get
    void onOwnProfileGetSuccess(Object profile);

    void onOwnProfileGetFailed(String Reason);

    //activity_profile GET call
    void onProfileGetSuccess(Object profile);

    void onProfileGetFailed(String Reason);

    //Pending Invites GET
    void onPendingInvitesSuccess(Invite[] invites);

    void onPendingInvitesFailed(String reason);

    //PUT invite accept petition
    void onInviteAnswerSuccess();

    void onInviteAnswerFailure(String reason);

    //GET accepted invites Petition
    void onAcceptedInvitesSuccess(Invite[] invites);

    void onAcceptedInvitesFailed(String reason);

    //GET profiles
    void onGetProfilesSuccess(CardOfPeople[] profiles);

    void onGetProfilesFailed(String reason);

    //get relationship


}