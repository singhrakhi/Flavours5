package com.flavours5.webservices;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseListner {
    private OnResponseInterface onResponseInterface;
    private String message;
    private FirebaseAnalytics mFirebaseAnalytics;

    int requestId;

    public ResponseListner(OnResponseInterface onResponseInterface) {
        this.onResponseInterface = onResponseInterface;
    }

    public void getResponse(Call call, Context mContext, int requestId) {
        this.requestId = requestId;
        getResponse(call, mContext);
    }

    public void getResponse(Call call, Context mContext) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                message = new Gson().toJson(response.toString());
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, message);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80]

                if (response.code() == 200)
                    onResponseInterface.onApiResponse(response.body(), requestId);
                else {
                    onResponseInterface.onApiFailure(message, requestId);

                    Date currentTime = Calendar.getInstance().getTime();
                    DatabaseReference myRef = database.getReference(currentTime + "");

                    myRef.setValue(message);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                message = t.getMessage();
                onResponseInterface.onApiFailure(message, requestId);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80]
                Date currentTime = Calendar.getInstance().getTime();

                DatabaseReference myRef = database.getReference(currentTime + "");

                myRef.setValue(message);

            }
        });
    }


}
