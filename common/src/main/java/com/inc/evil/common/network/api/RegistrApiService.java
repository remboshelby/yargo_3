package com.inc.evil.common.network.api;

import com.inc.evil.common.network.models.user_info.PersonData;
import com.inc.evil.common.network.models.user_info.RegistResponse;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrApiService {
    @POST("/signup")
    Observable<RegistResponse> makeRegistNewUser(@Body PersonData personData);

    //TODO подтверждение телефонного номера
}
