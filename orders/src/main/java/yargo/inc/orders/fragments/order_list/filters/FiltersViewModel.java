package yargo.inc.orders.fragments.order_list.filters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

import yargo.inc.common.base.BaseViewAndroidModel;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CategoryModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CityModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.SettingModel;

public class FiltersViewModel extends BaseViewAndroidModel {

    MutableLiveData <Boolean> isAllCategoryChecked = new MutableLiveData<>();


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

    String categoryNames[] = {
            "Сантехнические работы",
            "Электромонтажные работы",
            "Сварочные работы",
            "Отделочные работы",
            "Двери",
            "Замки",
            "Мебель",
            "Мелкий ремонт",
            "Окна",
            "Отопление",
            "Вентиляция",
            "Слесарные работы",
            "Столярные работы",
            "Токарные работы",
            "Общестроительные работы",
            "Информационные технологии",
            "Клининг",
            "Ремонт бытовой техники",
    };
    String item_name[] = {
            "caterogy_item_0",
            "caterogy_item_1",
            "caterogy_item_2",
            "caterogy_item_3",
            "caterogy_item_4",
            "caterogy_item_5",
            "caterogy_item_6",
            "caterogy_item_7",
            "caterogy_item_8",
            "caterogy_item_9",
            "caterogy_item_10",
            "caterogy_item_11",
            "caterogy_item_12",
            "caterogy_item_13",
            "caterogy_item_14",
            "caterogy_item_15",
            "caterogy_item_16",
            "caterogy_item_17"};
    private CommonSharedPreferences commonSharedPreferences;

//    public FiltersViewModel(CommonSharedPreferences commonSharedPreferences) {
//        this.commonSharedPreferences = commonSharedPreferences;
//    }

    public FiltersViewModel(@NonNull Application application,CommonSharedPreferences commonSharedPreferences) {
        super(application);
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public void observeIsAllCategoryChecked(LifecycleOwner lifecycleOwner, Observer observer){
        isAllCategoryChecked.observe(lifecycleOwner, observer);
    }
    public ArrayList<SettingModel> createSettingsArray() {
        ArrayList<SettingModel> settingsList = new ArrayList<>();
        int categoryCount = (int) commonSharedPreferences.getIntObject("category_count", int.class);
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
        for (int i =0; i<iconsCategory.length; i++){
            boolean test = (boolean)commonSharedPreferences.getBooleanObject(item_name[i], boolean.class);
            checkedAll = checkedAll&test;
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i,(boolean)commonSharedPreferences.getBooleanObject( item_name[i], boolean.class)));
        }
        isAllCategoryChecked.setValue(checkedAll);
        return categoryList;
    }

    public ArrayList<CategoryModel> dropChecked(){
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        for (int i =0; i<iconsCategory.length; i++){
            commonSharedPreferences.putObject(item_name[i], "false");
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i,false));
        }
        return categoryList;
    }
    public ArrayList<CategoryModel> setCheckedAll(){
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        for (int i =0; i<iconsCategory.length; i++){
            commonSharedPreferences.putObject(item_name[i], "true");
            categoryList.add(new CategoryModel(categoryNames[i], iconsCategory[i], i,true));
        }
        return categoryList;
    }
    public void setCheck(int position, boolean checked){
        commonSharedPreferences.putObject(item_name[position], checked);
    }
}
