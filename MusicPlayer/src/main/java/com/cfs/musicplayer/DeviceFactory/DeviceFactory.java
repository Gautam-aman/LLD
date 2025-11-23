package com.cfs.musicplayer.DeviceFactory;

import com.cfs.musicplayer.Enums.DeviceType;
import com.cfs.musicplayer.OutputDevice.BTSpeakerAdapter;
import com.cfs.musicplayer.OutputDevice.HeadPhoneAdapter;
import com.cfs.musicplayer.OutputDevice.IOAudioDevice;
import com.cfs.musicplayer.external.BTSpeakerApi;
import com.cfs.musicplayer.external.HeadPhoneApi;

import java.io.IOException;

public class DeviceFactory {

    public static IOAudioDevice createDevice(DeviceType deviceType) {
        if (deviceType == DeviceType.BLUETOOTH) {
            return new BTSpeakerAdapter(new BTSpeakerApi());
        }
        if  (deviceType == DeviceType.HEADPHONE) {
            return new HeadPhoneAdapter(new HeadPhoneApi());
        }
        return null;
    }

}
