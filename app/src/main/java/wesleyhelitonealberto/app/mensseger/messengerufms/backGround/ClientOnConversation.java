package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

public class ClientOnConversation {
    private static Contacts contact;
    private static boolean chatOpen;

    public static void setContact(Contacts contact) {
        ClientOnConversation.contact = contact;
        chatOpen = true;
    }
    public static Contacts getContactConnect() {
        return contact;
    }

    public static boolean anyChatOpen() {
        return chatOpen;
    }

    public static void setFalseChatOpen() {
        chatOpen = false;
    }
}
