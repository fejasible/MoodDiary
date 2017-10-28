package com.app.feja.mooddiary.widget.Search;


import android.content.Context;
import android.widget.Filter;

import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataHelper {

    private static final String COLORS_FILE_NAME = "colors.json";

    private static List<DiaryWrapper> sDiaryWrappers = new ArrayList<>();

    private static List<DiarySuggestion> sDiarySuggestions = new ArrayList<>();

    public interface OnFindDiaryListener {
        void onResults(List<DiaryWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<DiarySuggestion> results);
    }

    private static void initDiaryWrapperList(Context context) {

        if (sDiaryWrappers.isEmpty()) {
            String jsonString = loadJson(context);
            sDiaryWrappers = deserializeDiary(jsonString);
        }
    }

    private static String loadJson(Context context) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private static List<DiaryWrapper> deserializeDiary(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<DiaryWrapper>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }
    public static void resetSuggestionsHistory() {
        for (DiarySuggestion diarySuggestion : sDiarySuggestions) {
            diarySuggestion.setmIsHistory(false);
        }
    }
    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<DiarySuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    sDiarySuggestions.clear();
                    List<DiaryEntity> diaryEntities = new DiaryDaoImpl().getAllDiary();
                    for(DiaryEntity diaryEntity: diaryEntities){
                        sDiarySuggestions.add(new DiarySuggestion(diaryEntity.getContent()));
                    }
                    for (DiarySuggestion suggestion : sDiarySuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<DiarySuggestion>() {
                    @Override
                    public int compare(DiarySuggestion lhs, DiarySuggestion rhs) {
                        return lhs.ismIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<DiarySuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findColors(Context context, String query, final OnFindDiaryListener listener) {
        initDiaryWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<DiaryWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (DiaryWrapper color : sDiaryWrappers) {
                        if (color.getName().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(color);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<DiaryWrapper>) results.values);
                }
            }
        }.filter(query);

    }


}