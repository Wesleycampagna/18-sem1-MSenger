package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

public class Contacts {
    private String ip;
    private String LastMsg;
    private String indetification;

    public String getLastMsg() {
        return LastMsg;
    }

    public void setLastMsg(String lastMsg) {
        LastMsg = lastMsg;
    }

    public String getIndetification() {
        return indetification;
    }

    public void setIndetification(String indetification) {
        this.indetification = indetification;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}