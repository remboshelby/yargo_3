package yargo.inc.common.di;

import yargo.inc.common.base.BaseActivity;

public interface ApplicationNavigator {
    void openFragment(BaseActivity baseActivity, String fragmentName);
}
