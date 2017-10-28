package com.app.feja.mooddiary.widget.Search;


import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class DiarySuggestion implements SearchSuggestion{

    private String diaryString;
    private boolean mIsHistory = false;

    public static final Creator<DiarySuggestion> CREATOR = new Creator<DiarySuggestion>() {
        @Override
        public DiarySuggestion createFromParcel(Parcel in) {
            return new DiarySuggestion(in);
        }

        @Override
        public DiarySuggestion[] newArray(int size) {
            return new DiarySuggestion[size];
        }
    };

    public DiarySuggestion(Parcel in) {
        this.diaryString = in.readString();
        this.mIsHistory = in.readInt() != 0;
    }

    public DiarySuggestion(String suggestion){
        this.diaryString = suggestion.toLowerCase().substring(
                0, suggestion.length() > 9 ? 9 : suggestion.length());
    }


    @Override
    public String getBody() {
        return diaryString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diaryString);
        dest.writeInt(mIsHistory ? 1 : 0);
    }

    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }
}
