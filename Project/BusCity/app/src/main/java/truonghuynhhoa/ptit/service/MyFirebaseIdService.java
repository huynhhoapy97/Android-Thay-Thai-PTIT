package truonghuynhhoa.ptit.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    /**
     * Không phải lúc nào nó cũng vào
     * chỉ chạy 1 lần thôi, khi nào có sự thay đổi mới nó mới chạy vào lại
     * giả sử có 1 sự cố nào đó nó không lưu vào Remote Server của ta nhưng thực ra nó đã lưu trên Server của Firebase, lúc đó chúng ta sẽ không có cơ hội vào được đây.
     * khi đó chúng ta phải clear cache và install app này và chạy lại nó mới vào lại đây được và lưu token của thiết bị, vì vậy rất phức tạp và khách hàng không ai làm vậy
     * nên trong mainActivity chúng ta nên gọi asyncTask này một lần nữa để lấy token lưu vào Remote Server của chúng ta
      */
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        saveTokenIntoRemoteDatabase(token);
    }

    private void saveTokenIntoRemoteDatabase(String token) {
        FirebaseIdTask firebaseIdTask = new FirebaseIdTask();
        firebaseIdTask.execute(token);
    }
}
