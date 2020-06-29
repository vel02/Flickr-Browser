package kiz.learnwithvel.browser.util;

public class Constants {


    public static final String BASE_URL = "https://www.flickr.com/services/feeds/";
    public static final int CURRENT_TIME_IN_SECONDS = ((int) System.currentTimeMillis() / 1000);

    public static final int CONNECTION_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 2;
    public static final int WRITE_TIMEOUT = 2;

    private final static int SECONDS = 60;
    private final static int MINUTES = 60;
    private final static int HOURS = 24;

    public static final int PHOTO_REFRESH_TIME = SECONDS * MINUTES * HOURS; // 1 day (in seconds)

    public static final String[] DEFAULT_SEARCH_CATEGORIES =
            {"android", "bike", "goku", "instrument", "kobebryant", "luffy", "tesla", "vollyball"};

    public static final String[] DEFAULT_SEARCH_CATEGORIES_IMAGES =
            {
                    "android",
                    "bike",
                    "goku",
                    "instrument",
                    "kobebryant",
                    "luffy",
                    "tesla",
                    "vollyball"
            };

}
