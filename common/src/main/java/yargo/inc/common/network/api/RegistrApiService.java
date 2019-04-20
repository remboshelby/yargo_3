package yargo.inc.common.network.api;

import yargo.inc.common.network.models.user_info.PersonData;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;

public interface RegistrApiService {
    @POST("/signup")
    Observable<RegistrResponse> makeRegistNewUser(@Body PersonData personData);

    //TODO подтверждение телефонного номера
}
