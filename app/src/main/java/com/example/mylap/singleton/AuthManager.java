package com.example.mylap.singleton;

import androidx.lifecycle.MutableLiveData;

public class AuthManager {
    private static AuthManager instance;
    private MutableLiveData<Boolean> isLoginLiveData = new MutableLiveData<>();

    private AuthManager() {
        // Khởi tạo trạng thái đăng nhập mặc định
        isLoginLiveData.setValue(false);
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public MutableLiveData<Boolean> getIsLoginLiveData() {
        return isLoginLiveData;
    }

    public void setLoginStatus(boolean status) {
        isLoginLiveData.setValue(status);
    }
}
