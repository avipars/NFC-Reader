package com.aviparshan.nfcreader.utils;

import android.nfc.NfcAdapter;
import android.nfc.Tag;

import com.aviparshan.nfcreader.activities.Main;

public class NFCCardReader implements NfcAdapter.ReaderCallback {

    private Main mainActivity;

    public NFCCardReader(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        String tagId = bytesToHexString(tag.getId());
        mainActivity.displayTagId(tagId);
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (byte aSrc : src) {
            buffer[0] = Character.forDigit((aSrc >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(aSrc & 0x0F, 16);
            //System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }
}