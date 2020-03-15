package network_tool_and_pc_comparison;

public class PcInfo {

    String ip, pcName;
    boolean cb;

    PcInfo(String ip, String pcName) {

        this.ip = ip;
        this.pcName = pcName;

    }

    PcInfo(String ip, String pcName, boolean cb) {
        this.ip = ip;
        this.pcName = pcName;
        this.cb = cb;
    }

}
