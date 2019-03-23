package com.aviparshan.nfcreader.utils;

import android.nfc.NfcAdapter;
import android.nfc.Tag;

import com.aviparshan.nfcreader.activities.MainReaderActivity;

public class NFCCardReader implements NfcAdapter.ReaderCallback {

    private MainReaderActivity mainReaderActivityActivity;

    public NFCCardReader(MainReaderActivity mainReaderActivityActivity) {
        this.mainReaderActivityActivity = mainReaderActivityActivity;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        //TODO: display UID as string rather than hex
        String tagId = bytesToHexString(tag.getId()); //hex
        mainReaderActivityActivity.displayTagId(tagId);
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