package yargo.inc.common.network.repository;

import yargo.inc.common.network.models.user_info.RegistResponse;
import yargo.inc.common.network.api.RegistrApiService;
import yargo.inc.common.network.models.user_info.PersonData;

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
