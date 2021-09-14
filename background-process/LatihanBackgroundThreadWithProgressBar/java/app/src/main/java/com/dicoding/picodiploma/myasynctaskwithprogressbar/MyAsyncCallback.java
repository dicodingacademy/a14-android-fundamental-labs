package com.dicoding.picodiploma.myasynctaskwithprogressbar;

interface MyAsyncCallback {
    void onPreExecute();

    void onUpdateProgress(long value);

    void onPostExecute(String text);
}

