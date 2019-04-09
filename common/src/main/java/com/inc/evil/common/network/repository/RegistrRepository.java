package com.inc.evil.common.network.repository;

import com.inc.evil.common.network.api.RegistrApiService;
import com.inc.evil.common.network.models.user_info.PersonData;
import com.inc.evil.common.network.models.user_info.RegistResponse;

import io.reactivex.Observable;

public class RegistrRepository {
    private RegistrApiService registrApiService;

    public RegistrRepository(RegistrApiService registrApiService) {
        this.registrApiService = registrApiService;
    }
    public Observable<RegistResponse> makeRegistr(PersonData personData){
        return registrApiService.makeRegistNewUser(personData);
    }
}
