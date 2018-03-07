package com.ayogeshwaran.bakingapp.Utils;

import com.ayogeshwaran.bakingapp.Data.Model.Remote.RetrofitApiInterface;
import com.ayogeshwaran.bakingapp.Data.Model.Remote.RetrofitClient;

public class ApiUtils {

    public static RetrofitApiInterface getRetrofitClient(String url) {
        return RetrofitClient.getClient(url)
                .create(RetrofitApiInterface.class);
    }
}
