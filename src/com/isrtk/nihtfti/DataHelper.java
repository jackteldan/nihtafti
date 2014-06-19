package com.isrtk.nihtfti;

import android.content.Context;

import java.io.RandomAccessFile;

/**
 * Created by Avi on 13/05/2014.
 */
public class DataHelper {
    public static long contextReadLong(Context context, String name) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "r");
            return raf.readLong();
        } catch (Throwable t) {
            return 0;
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }

    public static void contextWriteLong(Context context, String name, long v) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "rwd");
            raf.setLength(0);
            raf.writeLong(v);
        } catch (Throwable t) {
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }
    public static int contextReadInt(Context context, String name) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "r");
            return raf.readInt();
        } catch (Throwable t) {
            return 0;
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }

    public static void contextWriteInt(Context context, String name, int v) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "rwd");
            raf.setLength(0);
            raf.writeInt(v);
        } catch (Throwable t) {
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }

    public static void contextWriteString(Context context, String name, String v) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "rwd");
            raf.setLength(0);
            raf.writeUTF(v);
        } catch (Throwable t) {
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }
    public static String contextReadString(Context context, String name) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(context.getFileStreamPath(name), "r");
            return raf.readUTF();
        } catch (Throwable t) {
            return null;
        } finally {
            try {
                raf.close();
            } catch (Throwable t) {}
        }
    }
}
