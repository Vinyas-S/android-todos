package com.example.todo_firebase;

import androidx.annotation.NonNull;

public class ViewHolder {

    private String simpleText;

    public ViewHolder(@NonNull final String simpleText) {
        setSimpleText(simpleText);
    }

    @NonNull
    public String getSimpleText() {
        return simpleText;
    }

    public void setSimpleText(@NonNull final String simpleText) {
        this.simpleText = simpleText;
    }
}
