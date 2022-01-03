package com.dicoding.newsapp.data;

public class Result<T> {
    private Result() {
    }

    public final static class Success<T> extends Result<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    public final static class Error<T> extends Result<T> {
        private final String error;

        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return this.error;
        }
    }

    public static class Loading<T> extends Result<T>{
    }
}