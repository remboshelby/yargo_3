package yargo.inc.common.network.api;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;

public interface RegistrApiService {
    @Headers("Content-Type: application/text")
    @POST("/signup")
    Observable<RegistrResponse> makeRegistNewUser(@Body HashMap<String, Object> jsonObject);

    @GET("/signupg")
    Observable<RegistrResponse> makeRegistNewUserGet(@Query("SignupForm[username]") String username,
                                                       @Query("SignupForm[surname]") String surname,
                                                       @Query("SignupForm[sex]") String sex,
                                                       @Query("SignupForm[email]") String email,
                                                       @Query("SignupForm[phone]") String phone,
                                                       @Query("SignupForm[birthday]") String birthday,
                                                       @Query("SignupForm[password]") String password,
                                                       @Query("SignupForm[id_city]") String id_city);
    //TODO подтверждение телефонного номера
}
