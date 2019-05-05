package yargo.inc.orders.fragments.order_list.filters.custom_view.models;

public class CategoryModel {
    private String titleCategory;
    private int categoryImg;
    private int id;
    private boolean checked;

    public CategoryModel(String titleCategory, int categoryImg, int id, boolean checked) {
        this.titleCategory = titleCategory;
        this.categoryImg = categoryImg;
        this.id = id;
        this.checked = checked;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
