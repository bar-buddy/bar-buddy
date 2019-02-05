package com.example.litness.litness;

import java.util.List;

public class Interface {
    public interface WithVoidListener{
        void onEvent();
    }
    public interface WithStringListener{
        void onEvent(String string);
    }
    public interface WithStringListListener{
        void onEvent(List<String> list);
    }
    public interface YesNoHandler{
        void onYesClicked();
        @SuppressWarnings("EmptyMethod")
        void onNoClicked();
    }
}