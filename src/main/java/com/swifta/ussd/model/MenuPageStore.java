package com.swifta.ussd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swifta.ussd.constant.AppMessages.*;

@Data
public class MenuPageStore {

    private static final int MAX_PAGE_LENGTH = 160;

    private String firstMessage;
    private List<MenuPageModel> menuPageModels;

    private Map<String, String> pageMessageMap;

    public MenuPageStore() {
    }

    public MenuPageStore(String firstMessage, List<MenuPageModel> menuPageModels) {
        this.firstMessage = firstMessage;
        this.menuPageModels = menuPageModels;
        loadMessage();
    }

    private void loadMessage() {
        pageMessageMap = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(firstMessage);

        int page = 1;
        int i = 0;
        for (; i < menuPageModels.size(); i++) {
            if (page == 1 && stringBuilder.length() > MAX_PAGE_LENGTH - FIRST_NAVIGATION_MESSAGE.length()) {
                String menu = stringBuilder.toString();
                pageMessageMap.put(
                        String.valueOf(page), menu.substring(0, menu.lastIndexOf("\n")).concat(FIRST_NAVIGATION_MESSAGE));
                page = page + 1;
                stringBuilder = new StringBuilder(menu.substring(menu.lastIndexOf("\n")));
            } else if (stringBuilder.length() > MAX_PAGE_LENGTH - MID_NAVIGATION_MESSAGE.length()) {
                String menu = stringBuilder.toString();
                pageMessageMap.put(
                        String.valueOf(page), menu.substring(0, menu.lastIndexOf("\n")).concat(MID_NAVIGATION_MESSAGE));
                page = page + 1;
                stringBuilder = new StringBuilder(menu.substring(menu.lastIndexOf("\n")));
            }
            stringBuilder.append("\n").append(i + 1).append(". ").append(menuPageModels.get(i).getStringValue());
        }

        String menu = stringBuilder.toString();
        if (page != 1 && menu.contains("\n")) {
            menu = menu.concat(NAVIGATION_MESSAGE);
        }
        pageMessageMap.put(String.valueOf(page), menu);
    }

    @JsonIgnore
    public MenuPageModel getMenuPageModel(int i) {
        return menuPageModels.get(i - 1);
    }

    @JsonIgnore
    public String getPage(Integer pageNo) {
        return this.pageMessageMap.get(pageNo.toString());
    }

    @JsonIgnore
    public int getPageSize() {
        return pageMessageMap.keySet().size();
    }

}
