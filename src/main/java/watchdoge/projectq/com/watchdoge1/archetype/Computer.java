package watchdoge.projectq.com.watchdoge1.archetype;

import java.util.List;

/**
 * Created by timurw10 on 17.03.2018.
 */

public class Computer {

    private String osName;
    private String osVersion;
    private String totalRAM;
    private String cpu;
    private String cpuCores;
    private String cpuLogicalCores;
    private String computerName;
    private String computerMac;
    private String computerIPv4;
    private String computerIPv6;

    private List<String> diskList;

    public String getOsVersion() {
        return osVersion;
    }

    public String getComputerIPv4() {
        return computerIPv4;
    }

    public String getComputerIPv6() {
        return computerIPv6;
    }

    public String getComputerName() {
        return computerName;
    }

    public String getOsName() {
        return osName;
    }

    public String getTotalRAM() {
        return totalRAM;
    }

    public String getCpuName() {
        return cpu;
    }

    public String getComputerMac() {
        return computerMac;
    }

    public List<String> getDiskList() {
        return diskList;
    }

    public String getCpu() {
        return cpu;
    }

    public String getCpuCores() {
        return cpuCores;
    }

    public String getCpuLogicalCores() {
        return cpuLogicalCores;
    }


}
