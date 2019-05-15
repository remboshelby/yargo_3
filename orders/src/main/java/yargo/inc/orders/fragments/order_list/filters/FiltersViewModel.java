package yargo.inc.orders.fragments.order_list.filters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

import yargo.inc.common.base.BaseViewAndroidModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.SettingModel;

public class FiltersViewModel extends BaseViewAndroidModel {

    MutableLiveData<Boolean> isAllCategoryChecked = new MutableLiveData<>();
    private String[] item_name;
    private String[] categoryNames;

    int iconsCategory[] = {
            R.drawable.icon_santech,
            R.drawable.icon_electro,
            R.drawable.icon_svarka,
            R.drawable.icon_paint,
            R.drawable.icon_dors,
            R.drawable.icon_lock,
            R.drawable.icon_mebel,
            R.drawable.icon_repair,
            R.drawable.icon_window,
            R.drawable.icon_gradusnik,
            R.drawable.icon_ventel,
            R.drawable.icon_drel,
            R.drawable.icon_pila,
            R.drawable.icon_tocar,
            R.drawable.icon_wall,
            R.drawable.icon_it,
            R.drawable.icon_clean,
            R.drawable.icon_bit,
    };

    private CommonSharedPreferences commonSharedPreferences;

    public FiltersViewModel(@NonNull Application application, CommonSharedPreferences commonSharedPreferences) {
        super(application);
        this.commonSharedPreferences = commonSharedPreferences;
        this.item_name = getApplication().getResources().getStringArray(R.array.item_name);
        this.categoryNames = getApplication().getResources().getStringArray(R.array.categoryName);
    }

    public void observeIsAllCategoryChecked(LifecycleOwner lifecycleOwner, Observer observer) {
        isAllCategoryChecked.observe(lifecycleOwner, observer);
    }

    public ArrayList<SettingModel> createSettingsArray() {
        ArrayList<SettingModel> settingsList = new ArrayList<>();
        int categoryCount = getFilterCategoryCount();
        if (categoryCount == 0) {
            settingsList.add(new SettingModel("Категории", "Все категории", R.drawable.ic_apps_black_24dp, 1));
        } else {
            settingsList.add(new SettingModel("Категории", "Выбранно категорий: " + categoryCount, R.drawable.ic_apps_black_24dp, 1));
        }
        settingsList.add(new SettingModel("Город", commonSharedPreferences.getFilteredCityName(), R.drawable.ic_location_on_ylow_24dp, 2));
        return settingsList;
    }

    public ArrayList<CategoryModel> createCategoryArray() {
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        boolean checkedAll = true;
        for (int i = 0; i < iconsCategory.length; i++) {
            boolean test = (boolean) commonSharedPreferences.getBooleanObject(item_name[i], boolean.class);
            checkedAll = checkedAll & test;
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i, (boolean) commonSharedPreferences.getBooleanObject(item_name[i], boolean.class)));
        }
        isAllCategoryChecked.setValue(checkedAll);
        return categoryList;
    }

    public ArrayList<CategoryModel> dropChecked() {
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        for (int i = 0; i < iconsCategory.length; i++) {
            commonSharedPreferences.putObject(item_name[i], "false");
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i, false));
        }
        return categoryList;
    }

    public ArrayList<CategoryModel> setCheckedAll() {
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        for (int i = 0; i < iconsCategory.length; i++) {
            commonSharedPreferences.putObject(item_name[i], "true");
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i, true));
        }
        return categoryList;
    }

    public void setCheck(int position, boolean checked) {
        commonSharedPreferences.putObject(item_name[position], checked);
    }
    public int getFilterCategoryCount(){
        int count = 0;
        for (int i = 0; i < iconsCategory.length; i++) {
            if ((Boolean)commonSharedPreferences.getBooleanObject(item_name[i], Boolean.class)) count++;
        }
        return count;
    }
}
