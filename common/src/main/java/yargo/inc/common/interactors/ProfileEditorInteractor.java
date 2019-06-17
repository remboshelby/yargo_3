package yargo.inc.common.interactors;

import io.reactivex.Observable;
import yargo.inc.common.base.BaseSharedPreference;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.profile_editor_model.ProfileEditResponse;
import yargo.inc.common.network.repository.LoginRepository;

public class ProfileEditorInteractor extends BaseSharedPreference {

    protected LoginRepository loginRepository;

    public ProfileEditorInteractor(CommonSharedPreferences commonSharedPreferences, LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        setCommonSharedPreferences(commonSharedPreferences);
    }

    public Observable<ProfileEditResponse> editProfile(String auth_key, String app_id, String username,
                                                                   String surname, int sex, int id_city, String birthday)  {
        return loginRepository.editProfile(auth_key, app_id, username,
                surname,
                sex,
                id_city,
                birthday);
    }

}
