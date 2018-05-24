package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.util.ArrayList;

public class ControlOfContacts {
    private static ArrayList <Contacts> contacts = new ArrayList<>();

    public static void printt() {
        for (Contacts contact : contacts) {
            System.out.println(contact.getIndetification());
            System.out.println(contact.getIp());
        }
    }

    public static ArrayList<Contacts> getAllContacts() {
        return contacts;
    }

    public static void addContact(Contacts contact) {
        contacts.add(contact);
    }

    public void removeContact(int position) {
        contacts.remove(position);
    }
}