package ru.aristov.ipv4_calculator;

import static java.lang.Math.pow;

public class IPv4_Calculation {
    public static int[] normalMask(int prefixNetmask) {
        int[] netmask = new int[4];
        int countZeroBitMask = 32 - prefixNetmask;

        if (countZeroBitMask == 0) {
            for (int i = 0; i < 4; i++) {
                netmask[i] = 255;
            }
        } else if (countZeroBitMask > 0 && countZeroBitMask <= 8) {
            for (int i = 0; i < (prefixNetmask / 8); i++) {
                netmask[i] = 255;
            }
            netmask[(prefixNetmask / 8)] = 256 - (int) pow(2, countZeroBitMask);
        } else if (countZeroBitMask > 8 && countZeroBitMask <= 16) {
            for (int i = 0; i < (prefixNetmask / 8); i++) {
                netmask[i] = 255;
            }
            countZeroBitMask -= 8;
            netmask[(prefixNetmask / 8)] = 256 - (int) pow(2, countZeroBitMask);
        } else if (countZeroBitMask > 16 && countZeroBitMask <= 24) {
            for (int i = 0; i < (prefixNetmask / 8); i++) {
                netmask[i] = 255;
            }
            countZeroBitMask -= 16;
            netmask[(prefixNetmask / 8)] = 256 - (int) pow(2, countZeroBitMask);
        } else if (countZeroBitMask > 24 && countZeroBitMask <= 32) {
            for (int i = 0; i < (prefixNetmask / 8); i++) {
                netmask[i] = 255;
            }
            countZeroBitMask -= 24;
            netmask[(prefixNetmask / 8)] = 256 - (int) pow(2, countZeroBitMask);
        }
        return netmask;
    }

    public static int[] getNetAddress(int[] ip, int[] mask) {
        int[] ipNetwork = new int[4];
        for (int i = 0; i < mask.length; i++)  {
            if (mask[i] < 255) {
                int a = 256 - mask[i];
                for (int j = ip[i]; j > 0; j--) {
                    if (j % a == 0) {
                        ipNetwork[i] = j;
                        break;
                    }
                }
            } else ipNetwork[i] = ip[i];
        }
        return ipNetwork;
    }

    public static int[] getBroadcastAddress(int[] netmask, int[] ipNetwork) {
        int[] broadcast = new int[4];
        for (int i = 0; i < netmask.length; i++) {
            if (netmask[i] == 255) {
                broadcast[i] = ipNetwork[i];
            } if (netmask[i] == 0)  {
                broadcast[i] = 255;
            } else {
                int a = 256 - netmask[i];
                broadcast[i] = ipNetwork[i] + (a-1);
            }
        }
        return broadcast;
    }

    public static long countHosts(int prefixNetmask) {
        if (prefixNetmask == 32) return 0;
        else return (long) pow(2, (32 - prefixNetmask));
    }
}
