package com.cds.jvm.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftRefQ {
    static ReferenceQueue<User> softQueue = null;

    public static class User {

        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }

    public static class CheckRefQueue extends Thread {
        @Override
        public void run() {
            while (true) {
                if (null != softQueue) {

                    UserSoftReference obj = null;
                    try {
                        obj = (UserSoftReference) softQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (obj != null) {
                        System.out.println("user id: " + obj.uid + " is deleted");
                    }
                }
            }
        }
    }

    public static class UserSoftReference extends SoftReference<User> {
        int uid;

        public UserSoftReference(User reference, ReferenceQueue<? super User> q) {
            super(reference, q);
            uid = reference.id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();
        User u = new User(1, "cds");
        softQueue = new ReferenceQueue<>();
        UserSoftReference userSoftReference = new UserSoftReference(u, softQueue);
        u = null;
        System.out.println(userSoftReference.get());
        System.gc();
        System.out.println("after gc");
        System.out.println(userSoftReference.get());

        System.out.println("try to create byte array and gc");

        byte[] b = new byte[1024 * 925 * 7];
        System.gc();
        System.out.println(userSoftReference.get());
        Thread.sleep(1000);
    }

}
