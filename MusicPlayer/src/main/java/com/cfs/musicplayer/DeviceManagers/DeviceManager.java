package com.cfs.musicplayer.DeviceManagers;

import com.cfs.musicplayer.DeviceFactory.DeviceFactory;
import com.cfs.musicplayer.Enums.DeviceType;
import com.cfs.musicplayer.OutputDevice.BTSpeakerAdapter;
import com.cfs.musicplayer.OutputDevice.IOAudioDevice;
import com.cfs.musicplayer.external.BTSpeakerApi;

import javax.management.RuntimeErrorException;

public class DeviceManager {
    private static DeviceManager instance = null;

    IOAudioDevice CurrentOutputDevice;

    DeviceManager(){
       this.CurrentOutputDevice = null;
    }


    public static DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    public void connetDevice(DeviceType deviceType) {
        if (CurrentOutputDevice != null) {
            CurrentOutputDevice = null;
        }
        this.CurrentOutputDevice = DeviceFactory.createDevice(deviceType);
        if (deviceType == DeviceType.BLUETOOTH) {
            System.out.println("Bluetooth Device Connected");
        }
        else if (deviceType == DeviceType.HEADPHONE) {
            System.out.println("Headphone Device Connected");
        }
    }

   public  IOAudioDevice GetCurrentOutputDevice() {
        if (CurrentOutputDevice == null) {
            throw new RuntimeException("No Device Connected");
        }
        return CurrentOutputDevice;
    }

    public boolean OutputDeviceConnected() {
        if (CurrentOutputDevice == null) {
            return false;
        }
        return true;
    }



}
