package com.ayogeshwaran.bakingapp.Data.Model.Remote;

import com.ayogeshwaran.bakingapp.Data.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
