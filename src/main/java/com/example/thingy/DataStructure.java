package com.example.thingy;

package com.example.computercommunicationgui2024;

import com.example.CommunicationData;

public class DataStructure {
    CommunicationData[] data = new CommunicationData[100];
    // field for next put location
    int nextPut = 0;
    // field for next get location
    int nextGet = 0;
    // field for space used
    int dataUsed = 0;

    public synchronized boolean put(CommunicationData obj) {
        if (dataUsed < 100) {
            dataUsed = dataUsed + 1;
            data[nextPut] = obj;
            if (nextPut < 99)  {
                nextPut = nextPut + 1;
            } else {
                nextPut = 0;
            }
            return true;
        } else {
            return false;
        }
    }

    // Methods
    public synchronized CommunicationData get() {
        if (dataUsed > 0) {
            CommunicationData value = data[nextGet];
            dataUsed = dataUsed - 1;
            if (nextGet < 99)  {
                nextGet = nextGet + 1;
            } else {
                nextGet = 0;
            }
            return value;
        } else {
            return null;
        }
    }

}