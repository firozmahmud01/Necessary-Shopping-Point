package com.firoz.mahmud.necessaryshoppingpoint;

import android.content.SearchRecentSuggestionsProvider;

public class SearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.firoz.mahmud.necessaryshoppingpoint.SearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public SearchProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
