package watchdoge.projectq.com.watchdoge1.archetype;

import java.util.LinkedList;

public class ComputerStatistic {

    private String cpuLoad;
    private String ramLoad;
    private String hddLoad;
    private String cpuTemp;
    private LinkedList<MyProcess> processList;
    private String computerName;
    private String upTime;
    private String date;

    public String getDate() {
        return date;
    }

    public String getCpuLoad() {
        return cpuLoad;
    }

    public String getRamLoad() {
        return ramLoad;
    }

    public String getHddLoad() {
        return hddLoad;
    }

    public String getCpuTemp() {
        return cpuTemp;
    }

    public LinkedList<MyProcess> getProcessList() {
        return processList;
    }

    public String getComputerName() {
        return computerName;
    }

    public String getUpTime() {
        return upTime;
    }
}

