package yargo.inc.common.network.api;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import yargo.inc.common.network.models.user_info.PersonData;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;
import yargo.inc.common.network.models.user_info.ResponseTest;

public interface RegistrApiService {
    @Headers("Content-Type: application/text")
    @POST("/signup")
    Observable<ResponseTest> makeRegistNewUser(@Body HashMap<String, Object> jsonObject);

    @GET("/signupg")
    Observable<ResponseTest> makeRegistNewUserGet(@Query("SignupForm[username]") String login,
                                                  @Query("SignupForm[sex]") String sex,
                                                  @Query("SignupForm[email]") String email,
                                                  @Query("SignupForm[phone]") String phone,
                                                  @Query("SignupForm[birthday]") String birthday,
                                                  @Query("SignupForm[password]") String password,
                                                  @Query("SignupForm[id_city]") String id_city);
    //TODO подтверждение телефонного номера
}
