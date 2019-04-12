package yargo.inc.common.network.api;

import yargo.inc.common.network.models.user_info.PersonData;
import yargo.inc.common.network.models.user_info.RegistResponse;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrApiService {
    @POST("/signup")
    Observable<RegistResponse> makeRegistNewUser(@Body PersonData personData);

    //TODO подтверждение телефонного номера
}
